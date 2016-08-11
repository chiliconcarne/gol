package GameOfLife.example.json;

/**
 * Created by sernowm on 11.08.2016.
 */
public class Message {
    private String msg;

    public Message(String msg) {
        this.setMsg(msg);
    }

    public void setMsg(String msg){
        this.msg=msg;
    }

    public String getMsg() {
        return msg;
    }
}
