package id.pdam.simdam.main.suin.shared;

public class Constant {
    public class HEADER {
        public static final String auth = "admin123";
        public static final String token = "4p1pdamr4h4s14";
    }

    public class API {
        public static final String URL = "http://10.21.28.187/myapipdam/public/";
        public static final String URL_LOGIN ="login/pegawai";

        public static final String URL_GET_SUIN_INBOX = "suin/getinbox";
        public static final String URL_GET_SUIN_KONTEN = "suin/getbaca";
        public static final String URL_GET_SUIN_PENERIMA = "suin/getpenerima";

        public static final String URL_POST_LAMPIRAN = "suin/uploadlampiran";
        public static final String URL_POST_SUIN = "suin/sendsuin";
        public static final String URL_POST_SUIN_BALAS = "suin/suinbalas";
        public static final String URL_POST_SUIN_DELETE = "suin/deletesuin";
        public static final String URL_POST_SUIN_SET_BACA = "suin/setbaca";
    }
    public class PREF{
        public static final String USER_ID = "user_id";
    }
}
