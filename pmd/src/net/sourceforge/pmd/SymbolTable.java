/*
 * User: tom
 * Date: Jun 19, 2002
 * Time: 9:31:16 AM
 */
package net.sourceforge.pmd;

import java.util.*;

public class SymbolTable {

    private SymbolTable parent;
    private Map usageCounts = new HashMap();
	private static final Integer ZERO = new Integer(0);
	private static final Integer ONE = new Integer(1);

    public SymbolTable() {}

    public SymbolTable(SymbolTable parent) {
        this.parent = parent;
    }

    public SymbolTable getParent() {
        return parent;
    }

    public void add(Symbol symbol) {
        if (usageCounts.containsKey(symbol)) {
            throw new RuntimeException(symbol + " is already in the symbol table");
        }
        usageCounts.put(symbol, ZERO);
    }

    public void recordPossibleUsageOf(Symbol symbol) {
        if (!usageCounts.containsKey(symbol) && parent != null) {
            parent.recordPossibleUsageOf(symbol);
            return;
        }
        if (!usageCounts.containsKey(symbol) ) {
            return;
        }
        usageCounts.put(symbol, ONE);
    }

    public Iterator getUnusedSymbols() {
        List list = new ArrayList();
        for (Iterator i = usageCounts.keySet().iterator(); i.hasNext();) {
            Symbol symbol = (Symbol)i.next();
            if (((Integer)usageCounts.get(symbol)).equals(ZERO)) {
                list.add(symbol);
            }
        }
        return list.iterator();
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (Iterator i = usageCounts.keySet().iterator(); i.hasNext();) {
            Symbol symbol = (Symbol)i.next();
            int usageCount = ((Integer)(usageCounts.get(symbol))).intValue();
            buf.append(symbol + "," + usageCount +":");
        }
        return buf.toString();
    }

    protected boolean contains(Symbol symbol) {
        if (usageCounts.containsKey(symbol)) {
            return true;
        }
        if (parent == null) {
            return false;
        }
        return parent.contains(symbol);
    }

}
