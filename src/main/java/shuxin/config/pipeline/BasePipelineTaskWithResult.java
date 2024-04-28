
package shuxin.config.pipeline;

import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public abstract class BasePipelineTaskWithResult<T> {
    public BasePipelineTaskWithResult() {
    }

    public abstract Response<T> exec(Pipeline var1);

    public T run(Pipeline pipeline) {
        Response<T> results = this.exec(pipeline);
        pipeline.syncAndReturnAll();
        return results.get();
    }
}
