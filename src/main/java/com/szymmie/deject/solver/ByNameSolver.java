package com.szymmie.deject.solver;

import com.szymmie.deject.retrospect.InjectException;
import com.szymmie.deject.retrospect.Provider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ByNameSolver implements Solver {
    private final Map<String, Solver> nameMapping;

    public ByNameSolver() {
        this.nameMapping = new HashMap<>();
    }

    public void link(String name, Solver solver) {
        if (this.nameMapping.containsKey(name)) {
            String reason = String.format("cannot link solver, name '%s' is already taken", name);
            throw new InjectException(solver.getClass(), reason);
        }
        this.nameMapping.put(name, solver);
    }

    public Optional<Solver> find(String key) {
        return Optional.ofNullable(this.nameMapping.get(key));
    }

    public Optional<Solver> findDefault() {
        return this.find(null);
    }

    @Override
    public Optional<Solver> accept(SolverVisitor visitor) {
        return visitor.visitByName(this);
    }

    @Override
    public Optional<Provider> resolve() {
        return Optional.empty();
    }
}
