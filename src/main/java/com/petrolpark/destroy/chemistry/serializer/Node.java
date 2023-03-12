package com.petrolpark.destroy.chemistry.serializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.petrolpark.destroy.chemistry.Atom;
import com.petrolpark.destroy.chemistry.Bond.BondType;

public class Node {
    private Atom atom;
    public Boolean visited;
    private List<Edge> edges;
    private Branch branch;
    private Map<Branch, BondType> sideBranches;

    public Node(Atom atom) {
        this.atom = atom;
        visited = false;
        edges = new ArrayList<>();
        sideBranches = new HashMap<>();
    };

    @SuppressWarnings("null") // It's not null I checked
    public String serialize() {
        String string = getAtom().getElement().getSymbol();
        Boolean isTerminal = true;
        Edge nextEdge = null;
        for (Edge edge : edges) {
            if (edge.getSourceNode() == this) {
                isTerminal = false;
                nextEdge = edge;
                break;
            };
        };
        if (atom.isAcidicProton()) {
            string += "+"+atom.getpKa();
        };
        if (!isTerminal) {
            string += nextEdge.bondType.getFROWNSCode(); // It thinks 'nextEdge' can be null
        };
        for (Branch branch : sideBranches.keySet()) {
            string += "(" + sideBranches.get(branch).getFROWNSCode() + branch.serialize() + ")"; // It thinks "nextEdge" is null
        };
        if (!isTerminal) {
            string += nextEdge.getDestinationNode().serialize();
        };
        return string;
    };

    public Atom getAtom() {
        return this.atom;
    };

    public Node addEdge(Edge edge) {
        edges.add(edge);
        return this;
    };

    public Node deleteEdge(Edge edge) {
        edges.remove(edge);
        return this;
    };

    public List<Edge> getEdges() {
        return edges;
    };

    public Node setBranch(Branch branch) {
        this.branch = branch;
        return this;
    };

    public Branch getBranch() {
        return this.branch;
    };

    public Node addSideBranch(Branch branch, BondType bondType) {
        sideBranches.put(branch, bondType);
        return this;
    };

    public Map<Branch, BondType> getSideBranches() {
        return sideBranches;
    };
};