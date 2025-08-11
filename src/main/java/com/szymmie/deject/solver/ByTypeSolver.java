package com.szymmie.deject.solver;

import com.szymmie.deject.retrospect.Provider;
import com.szymmie.deject.retrospect.Types;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ByTypeSolver implements Solver {
    private final Map<Class<?>, Solver> typeMapping;

    public ByTypeSolver() {
        this.typeMapping = new HashMap<>();
    }

    public void link(Class<?> type, Solver solver) {
        if (this.typeMapping.containsKey(type)) {
            String name = Types.getTypeName(type);
            String reason = String.format("cannot link solver, type '%s' is already taken", name);
            throw new SolverException(reason);
        }
        this.typeMapping.put(type, solver);
    }

    public Optional<Solver> find(Class<?> key) {
        return Optional.ofNullable(this.typeMapping.get(key));
    }

    @Override
    public Optional<Solver> accept(SolverVisitor visitor) {
        return visitor.visitByType(this);
    }

    @Override
    public Optional<Provider> resolve() {
        return Optional.empty();
    }
}
