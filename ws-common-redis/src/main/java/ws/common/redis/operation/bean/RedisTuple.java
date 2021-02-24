package ws.common.redis.operation.bean;

import redis.clients.jedis.Tuple;

public class RedisTuple {
    private String member;
    private Double score;

    public RedisTuple(String member, double score) {
        this.member = member;
        this.score = score;
    }

    public RedisTuple(Tuple tuple) {
        this.member = tuple.getElement();
        this.score = tuple.getScore();
    }

    public String getMember() {
        return member;
    }

    public Double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "[" + member + ":" + score + "]";
    }
}
