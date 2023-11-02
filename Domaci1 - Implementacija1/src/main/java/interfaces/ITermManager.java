package interfaces;

    public interface ITermManager {
    ITermCreator getTermCreator();
    ITermQueryEngine getTermQueryEngine();

    ITermFileLoader getTermFileLoader();

    ITermFileSaver getTermFileSaver();
}
