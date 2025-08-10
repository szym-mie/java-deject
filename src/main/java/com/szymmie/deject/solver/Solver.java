package com.szymmie.deject.solver;

import com.szymmie.deject.retrospect.Provider;

import java.util.Optional;

public interface Solver {
    Optional<Solver> accept(SolverVisitor visitor);
    Optional<Provider> resolve();
}
