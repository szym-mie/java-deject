package com.szymmie.deject.solver;

import com.szymmie.deject.retrospect.Provider;

import java.util.Optional;

public class ProviderSolver implements Solver {
    private final Provider provider;

    public ProviderSolver(Provider provider) {
        this.provider = provider;
    }

    @Override
    public Optional<Solver> accept(SolverVisitor visitor) {
        return Optional.empty();
    }

    @Override
    public Optional<Provider> resolve() {
        return Optional.of(this.provider);
    }
}
