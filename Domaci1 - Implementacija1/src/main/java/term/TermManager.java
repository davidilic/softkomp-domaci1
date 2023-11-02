package term;

import interfaces.ITermFileLoader;
import interfaces.ITermFileSaver;
import interfaces.ITermManager;

import java.util.HashMap;

public class TermManager implements ITermManager {
    private final TermCreator termCreator;
    private final TermQueryEngine termQueryEngine;
    private final ITermFileLoader termFileLoader;
    private final ITermFileSaver termFileSaver;

    public TermManager(ITermFileLoader termFileLoader, ITermFileSaver termFileSaver) {
        TermListMap termListMap = new TermListMap();
        this.termCreator = new TermCreator(termListMap, new HashMap<>());
        this.termQueryEngine = new TermQueryEngine(termListMap);
        this.termFileLoader = termFileLoader;
        this.termFileSaver = termFileSaver;
    }

    @Override
    public TermCreator getTermCreator() {
        return this.termCreator;
    }

    @Override
    public TermQueryEngine getTermQueryEngine() {
        return this.termQueryEngine;
    }

    @Override
    public ITermFileLoader getTermFileLoader() { return this.termFileLoader; }

    @Override
    public ITermFileSaver getTermFileSaver() { return this.termFileSaver; }
}