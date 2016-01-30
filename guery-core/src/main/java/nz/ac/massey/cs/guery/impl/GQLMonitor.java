package nz.ac.massey.cs.guery.impl;

public class GQLMonitor implements GQLMonitorMBean {

    private int vertexCount = 0;
    private int processedVertexCount = 0;

    @Override
    public int getVertexCount() {
        return this.vertexCount;
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    @Override
    public int getProcessedVertexCount() {
        return this.processedVertexCount;
    }

    public void setProcessedVertexCount(int processedVertexCount) {
        this.processedVertexCount = processedVertexCount;
    }

    public void setUnProcessedVertexCount(int c) {
        this.processedVertexCount = vertexCount - c;
    }

}
