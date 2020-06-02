package ir.coleo.chayi.pipline;

public interface Caller {

    NetworkData before(NetworkData data);

    NetworkData work(NetworkData data);

    NetworkData after(NetworkData data);


}
