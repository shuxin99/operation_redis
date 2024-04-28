
package shuxin.config.pipeline;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public abstract class BasePipelineTaskWithResultMap<T> {
    public BasePipelineTaskWithResultMap() {
    }

    public abstract Map<String, Response<T>> exec(Pipeline var1);

    public Map<String, T> run(Pipeline p) {
        Map<String, Response<T>> results = this.exec(p);
        p.syncAndReturnAll();
        Map<String, T> vals = new HashMap<>(results.size());
        Iterator var4 = results.entrySet().iterator();

        while (var4.hasNext()) {
            Map.Entry<String, Response<T>> e = (Map.Entry) var4.next();
            vals.put(e.getKey(), e.getValue().get());
        }

        return vals;
    }
}
