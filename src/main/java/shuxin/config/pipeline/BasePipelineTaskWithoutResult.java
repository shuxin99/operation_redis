
package shuxin.config.pipeline;

import redis.clients.jedis.Pipeline;

public abstract class BasePipelineTaskWithoutResult {
    public BasePipelineTaskWithoutResult() {
    }

    public abstract void exec(Pipeline var1);

    public void run(Pipeline pipeline) {
        this.exec(pipeline);
        pipeline.sync();
    }
}
