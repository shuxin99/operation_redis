package shuxin.common;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shuxin.constant.BusinessConstant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class R<T> {

    private String code;

    private T data;

    public String success(T data) {
        R<T> r = new R<>();
        r.setCode("0000");
        r.setData(data);
        return JSON.toJSONString(r);
    }

    public String success() {
        R<String> r = new R<>();
        r.setCode("0000");
        r.setData(BusinessConstant.SUCCESS);
        return JSON.toJSONString(r);
    }

    public String fail(T data) {
        R<T> r = new R<>();
        r.setCode("0001");
        r.setData(data);
        return JSON.toJSONString(r);
    }

    public String fail() {
        R<String> r = new R<>();
        r.setCode("0001");
        r.setData(BusinessConstant.FAIL);
        return JSON.toJSONString(r);
    }

}
