package pck;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import kodkod.ast.*;
import kodkod.instance.*;
import kodkod.engine.*;
import kodkod.engine.satlab.SATFactory;

public class PathIF {

	private final Relation Node, Start, Finish;

	private final Relation Edge, begin, end;

	private final Relation Visit, ref, next, start_loop, end_loop, loop_set;

	public PathIF() {														/* Path */
		Node = Relation.unary("Node");
		Edge = Relation.unary("Edge");
		Visit = Relation.unary("Visit");
		start_loop = Relation.unary("start_loop");
		end_loop = Relation.unary("end_loop");
		loop_set = Relation.unary("loop_set");


		begin = Relation.binary("begin");
		end = Relation.binary("end");
		ref = Relation.binary("ref");
		next = Relation.binary("next");

		Start = Relation.unary("Start");
		Finish = Relation.unary("Finish");
	}

	public Formula declarations() {
		final Formula f0 = begin.function(Edge, Node);
		final Formula f1 = end.function(Edge, Node);
		final Formula f2 = ref.function(Visit, Edge);						/* Node */
		final Formula f3 = next.partialFunction(Visit, Visit);
		return f0.and(f1).and(f2).and(f3);
	}

	public final Formula facts() {
		final Variable v = Variable.unary("v");
		final Variable w = Variable.unary("w");
		final Variable e = Variable.unary("e");								/* n */
		final Variable d = Variable.unary("d");								/* d */
		final Variable n = Variable.unary("n");
		final Variable st = Variable.unary("start_loop");
		final Variable en = Variable.unary("en");
	//	final Variable end = Variable.unary("end");
		/*	final Variable e = Variable.unary("e");	*/

		/* CONFORMITY: The structure of the path conforms to the structure of the graph. */
		final Formula f0 = v.join(next).eq(w);
		final Formula f1 = v.join(ref).eq(e);								/* n */
		final Formula f2 = w.join(ref).eq(d);								/* m */
		final Formula f3 = d.join(begin).eq(e.join(end));					/* e	n */
		/*	final Formula f4 = e.join(end).eq(m);	*/
		final Formula f4 = f0.and(f1).and(f2).implies(f3);
		final Formula f5 = f4.forAll(v.oneOf(Visit).and(w.oneOf(Visit)).and(e.oneOf(Edge)).and(d.oneOf(Edge)));

		/* ACYCLICITY: The path is an acyclic sequence of Visits. */
		final Formula f6 = v.in(w.join(next.reflexiveClosure()));
		final Formula f7 = w.in(v.join(next.closure())).not();
		final Formula f8 = f6.iff(f7).forAll(v.oneOf(Visit).and(w.oneOf(Visit)));

		/* There is a Visit before all other Visits, which references an Edge that Begins at the Start Node. */
		final Formula f9 = v.join(ref.join(begin)).eq(Start);
		final Formula f10 = w.in(v.join(next.reflexiveClosure()));
		final Formula f11 = f9.and(f10);
		final Formula f12 = f11.forSome(v.oneOf(Visit)).forAll(w.oneOf(Visit));

		/* There is a Visit after all other Visits, which references an Edge that Ends at the Finish Node. */
		final Formula f13 = v.join(ref.join(end)).eq(Finish);
		final Formula f14 = v.in(w.join(next.reflexiveClosure()));
		final Formula f15 = f13.and(f14);
		final Formula f16 = f15.forSome(v.oneOf(Visit)).forAll(w.oneOf(Visit));
		// rechablefromN = set of nodes reachable from N.
		// nodeb4N = going from beginning to end... set ofnodes that come before the node N
		
		// start loop nodes are nodes such that their transitive closure contains the node itself, but the node that comes before
		// is not contained within the transitive closure.
		final Expression reachableFromN = (n.join(((begin.transpose()).join(end)).closure()));
		final Expression nodeb4N       = (n.join(end.transpose() )).join(begin);
		final Formula f17 = n.in(reachableFromN);
		final Formula f18 = (nodeb4N.in(reachableFromN)).not();
		final Formula f19 = n.in(start_loop);
		final Formula f20 = (f18.and(f17)  ).iff(f19);
		final Formula f21 = f20.forAll((n.oneOf(Node)));
		
		final Expression begEnd = ((begin.transpose()).join(end)).closure(); // nodes reachable from node in question.
        final Expression nextNode = (((n.join(begin.transpose())).join(end))).difference(st); // next node, so long as its not the start node in question.
		final Formula f22 = n.in((st.join(begEnd))); // node in question reachable from start node in question.
		final Formula f23 = st.in(       nextNode.join(begEnd)          ).not();   // start node is not reachable from the NEXT node after n.
		final Formula f24 = n.in(end_loop);
		final Formula f25 = (n.in(Finish)).not(); // keep the bloody SAT solver from using a finishing nodes empty transitive closure to break my rules.
		final Formula f26 = f22.and(f23).and(f25).iff(f24);
		final Formula f27 = f26.forAll(n.oneOf(Node).and(st.oneOf(start_loop)));
		
		
		// nodes in the loop set is the nodes reachable from the start node, minus the nodes reachable from the node after the end node.. so long as that NEXT node is not the start node.
		// expression is the node after the end node. so long as it's not the start node.
		final Expression afterEnd = (((en.join(begin.transpose())).join(end))).difference(st);
		final Expression reachableFromStart = st.join(begEnd);
		final Formula f28 = n.in(loop_set);
		final Formula f29 = n.in((reachableFromStart.difference(afterEnd.join(begEnd))).difference(afterEnd));
		final Formula f30 = f28.iff(f29);
		final Formula f31 = f30.forAll(n.oneOf(Node).and(st.oneOf(start_loop)).and(en.oneOf(end_loop)));

		
		
		
		
		
	
		return f5.and(f8).and(f12).and(f16).and(f21).and(f27).and(f31);
	}

	public final Formula empty() {
		return declarations().and(facts());
	}

	public final Bounds bounds(int scope) {
		assert scope > 0;
		final int n = scope + 13;
		final List<String> atoms = new ArrayList<String>(n);
		for (int i = 1; i <= 4; i++)
			atoms.add("Node" + i);
		for (int i = 1; i <= 4; i++)
			atoms.add("Edge" + i);
		for (int i = 0; i < scope; i++)
			atoms.add("Visit" + i);

		final Universe u = new Universe(atoms);
		final TupleFactory f = u.factory();
		final Bounds b = new Bounds(u);

		final int max = scope - 1;

		b.bound(Node, f.range(f.tuple("Node1"), f.tuple("Node4")));				/* Java will not instantiate new Nodes. */
		b.bound(Edge, f.range(f.tuple("Edge1"), f.tuple("Edge4")));				/* Java will not instantiate new Edges. */
		b.bound(Visit, f.range(f.tuple("Visit0"), f.tuple("Visit" + max)));
		b.bound(start_loop, f.range(f.tuple("Node1"), f.tuple("Node4")));
		b.bound(end_loop, f.range(f.tuple("Node1"), f.tuple("Node4")));
		b.bound(loop_set, f.range(f.tuple("Node1"), f.tuple("Node4")));
		
		b.bound(ref, b.upperBound(Visit).product(b.upperBound(Edge)));		/* Node */
		b.bound(next, b.upperBound(Visit).product(b.upperBound(Visit)));
		
		
		final TupleSet Next = f.noneOf(2);
		for(Integer i = 0; i < scope - 1; i++){
			Integer plusone = i + 1;
			Next.add(f.tuple("Visit"+i, "Visit"+plusone));
		}
		b.boundExactly(next, Next);

		final TupleSet Begins = f.noneOf(2);
		Begins.add(f.tuple("Edge1", "Node1"));
		Begins.add(f.tuple("Edge2", "Node2"));
		Begins.add(f.tuple("Edge3", "Node3"));
		Begins.add(f.tuple("Edge4", "Node3"));
		b.boundExactly(begin , Begins);

		final TupleSet Ends = f.noneOf(2);
		Ends.add(f.tuple("Edge1", "Node2"));
		Ends.add(f.tuple("Edge2", "Node3"));
		Ends.add(f.tuple("Edge3", "Node4"));
		Ends.add(f.tuple("Edge4", "Node2"));
		b.boundExactly(end , Ends);

		final TupleSet Node1 = f.noneOf(1);									/* Node1 */
		Node1.add(f.tuple("Node1"));										/* Node1 */
		b.boundExactly(Start , Node1);										/* Node1 */

		final TupleSet Node4 = f.noneOf(1);									/* Node4 */
		Node4.add(f.tuple("Node4"));										/* Node4 */
		b.boundExactly(Finish , Node4);										/* Node4 */

		return b;
	}
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		try {
			final PathIF model = new PathIF();							/* Path		Path */
			final Solver solver = new Solver();
			final Bounds b = model.bounds(3);
			final Formula f = model.empty();
			System.out.println(f);
			solver.options().setSolver(SATFactory.DefaultSAT4J);
			System.out.println(System.currentTimeMillis());
			Iterator iterSols = solver.solveAll(f , b);
			System.out.println(System.currentTimeMillis());
			while(iterSols.hasNext()) {
				final Solution s = (Solution) iterSols.next();
				if(s.outcome() == Solution.Outcome.SATISFIABLE || s.outcome() == Solution.Outcome.TRIVIALLY_SATISFIABLE){
					System.out.println(s);	
				}
			}

		}	catch (NumberFormatException nfe) {}
	}
}
