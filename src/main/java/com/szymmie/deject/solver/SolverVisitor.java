package com.szymmie.deject.solver;

import com.szymmie.deject.retrospect.BindPoint;

import java.util.Optional;

public class SolverVisitor {
    private final BindPoint bindPoint;

    public SolverVisitor(BindPoint bindPoint) {
        this.bindPoint = bindPoint;
    }

    public Optional<Solver> visitByType(ByTypeSolver byTypeSolver) {
        return byTypeSolver.find(this.bindPoint.getType());
    }

    public Optional<Solver> visitByName(ByNameSolver byNameSolver) {
        Optional<Solver> maybeSolver = byNameSolver.find(this.bindPoint.getName());
        return maybeSolver.isPresent() ? maybeSolver : byNameSolver.findDefault();
    }
}
