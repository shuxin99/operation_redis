package shuxin.config;

public enum RedisType {
    Cluster, Sentinel, Standalone;

    private RedisType() {
    }

    public static RedisType parse(String statusCode) {
        RedisType[] var1 = values();
        int var2 = var1.length;
        for (int var3 = 0; var3 < var2; ++var3) {
            RedisType status = var1[var3];
            if (status.name().equalsIgnoreCase(statusCode)) {
                return status;
            }
        }
        return null;
    }
}
