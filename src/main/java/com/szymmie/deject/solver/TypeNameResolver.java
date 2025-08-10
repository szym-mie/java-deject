package com.szymmie.deject.solver;

import com.szymmie.deject.annotations.Provides;
import com.szymmie.deject.retrospect.BindPoint;
import com.szymmie.deject.retrospect.Provider;
import com.szymmie.deject.Module;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TypeNameResolver {
    private final ByTypeSolver typeSolver;

    public TypeNameResolver(Module module) {
        this.typeSolver = new ByTypeSolver();
        Map<Class<?>, ByNameSolver> nameSolverMap = new HashMap<>();

        for (Method method : module.getClass().getMethods()) {
            if (method.getAnnotation(Provides.class) == null) continue;

            Provider provider = new Provider(module, method);
            ProviderSolver providerSolver = new ProviderSolver(provider);
            Class<?> type = provider.getType();
            String name = provider.getName();

            if (!nameSolverMap.containsKey(type)) {
                ByNameSolver nameSolver = new ByNameSolver();
                nameSolver.link(name, providerSolver);
                nameSolverMap.put(type, nameSolver);
            } else {
                nameSolverMap.get(type).link(name, providerSolver);
            }
        }

        for (Class<?> type : nameSolverMap.keySet()) {
            this.typeSolver.link(type, nameSolverMap.get(type));
        }
    }

    public Optional<Provider> resolve(BindPoint bindPoint) {
        SolverVisitor visitor = new SolverVisitor(bindPoint);
        return Optional.of(this.typeSolver)
                .flatMap(typeSolver -> typeSolver.accept(visitor))
                .flatMap(nameSolver -> nameSolver.accept(visitor))
                .filter(providerSolver -> providerSolver instanceof ProviderSolver)
                .flatMap(Solver::resolve);
    }
}
