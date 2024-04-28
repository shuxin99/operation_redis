package shuxin.config.pipeline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public abstract class BasePipelineTaskWithResultList<T> {
    public BasePipelineTaskWithResultList() {
    }

    public abstract List<Response<T>> exec(Pipeline var1);

    public List<T> run(Pipeline pipeline) {
        List<Response<T>> results = this.exec(pipeline);
        pipeline.syncAndReturnAll();
        List<T> vals = new ArrayList<>(results.size());
        Iterator var4 = results.iterator();

        while(var4.hasNext()) {
            Response<T> r = (Response)var4.next();
            vals.add(r.get());
        }

        return vals;
    }
}
