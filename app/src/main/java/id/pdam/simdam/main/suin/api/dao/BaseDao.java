package id.pdam.simdam.main.suin.api.dao;

public class BaseDao<T> {
    public Boolean STATUS;
    public int STATUS_CODE;
    public MESSAGE MESSAGE;
    public T DATA;

    public class MESSAGE {
        public String PROD;
        public String DEVEL;
    }

}
