package ws.common.redis.operation;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.BitPosParams;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;
import ws.common.redis.operation.RedisOprationEnum.RedisDataStructureOp;

public class RedisParams {

    public static interface Params {
        RedisDataStructureOp<? extends AbstractBuilder, ?> getOp();
    }

    public static abstract class _Params implements Params {
        protected int dbIdx;
        protected RedisDataStructureOp<? extends AbstractBuilder, ?> op;

        public _Params(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
            this.dbIdx = dbIdx;
            this.op = op;
        }

        public int getDbIdx() {
            return dbIdx;
        }

        @Override
        public RedisDataStructureOp<? extends AbstractBuilder, ?> getOp() {
            return op;
        }
    }

    public static abstract class AbstractBuilder {
        protected int dbIdx;
        protected RedisDataStructureOp<? extends AbstractBuilder, ?> op;

        public AbstractBuilder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
            this.dbIdx = dbIdx;
            this.op = op;
        }
    }

    public static class Params_100 extends _Params {
        private int arg1;
        private String arg2;

        public Params_100(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, int arg1, String arg2) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        public int getArg1() {
            return arg1;
        }

        public String getArg2() {
            return arg2;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_100 build(int arg1, String arg2) {
                return new Params_100(dbIdx, op, arg1, arg2);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            return builder.toString();
        }
    }

    public static class Params_110 extends _Params {
        private String arg1;

        public Params_110(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1) {
            super(dbIdx, op);
            this.arg1 = arg1;
        }

        public String getArg1() {
            return arg1;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_110 build(String arg1) {
                return new Params_110(dbIdx, op, arg1);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            return builder.toString();
        }
    }

    public static class Params_120 extends _Params {
        private String arg1;
        private boolean arg2;

        public Params_120(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, boolean arg2) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        public String getArg1() {
            return arg1;
        }

        public boolean getArg2() {
            return arg2;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_120 build(String arg1, boolean arg2) {
                return new Params_120(dbIdx, op, arg1, arg2);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            return builder.toString();
        }
    }

    public static class Params_130 extends _Params {
        private String arg1;
        private boolean arg2;
        private BitPosParams arg3;

        public Params_130(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, boolean arg2, BitPosParams arg3) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
        }

        public String getArg1() {
            return arg1;
        }

        public boolean getArg2() {
            return arg2;
        }

        public BitPosParams getArg3() {
            return arg3;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_130 build(String arg1, boolean arg2, BitPosParams arg3) {
                return new Params_130(dbIdx, op, arg1, arg2, arg3);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            builder.append("arg3", arg3);
            return builder.toString();
        }
    }

    public static class Params_140 extends _Params {
        private String arg1;
        private double arg2;

        public Params_140(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, double arg2) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        public String getArg1() {
            return arg1;
        }

        public double getArg2() {
            return arg2;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_140 build(String arg1, double arg2) {
                return new Params_140(dbIdx, op, arg1, arg2);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            return builder.toString();
        }
    }

    public static class Params_150 extends _Params {
        private String arg1;
        private double arg2;
        private double arg3;

        public Params_150(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, double arg2, double arg3) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
        }

        public String getArg1() {
            return arg1;
        }

        public double getArg2() {
            return arg2;
        }

        public double getArg3() {
            return arg3;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_150 build(String arg1, double arg2, double arg3) {
                return new Params_150(dbIdx, op, arg1, arg2, arg3);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            builder.append("arg3", arg3);
            return builder.toString();
        }
    }

    public static class Params_160 extends _Params {
        private String arg1;
        private double arg2;
        private double arg3;
        private int arg4;
        private int arg5;

        public Params_160(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, double arg2, double arg3, int arg4, int arg5) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
            this.arg4 = arg4;
            this.arg5 = arg5;
        }

        public String getArg1() {
            return arg1;
        }

        public double getArg2() {
            return arg2;
        }

        public double getArg3() {
            return arg3;
        }

        public int getArg4() {
            return arg4;
        }

        public int getArg5() {
            return arg5;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_160 build(String arg1, double arg2, double arg3, int arg4, int arg5) {
                return new Params_160(dbIdx, op, arg1, arg2, arg3, arg4, arg5);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            builder.append("arg3", arg3);
            builder.append("arg4", arg4);
            builder.append("arg5", arg5);
            return builder.toString();
        }
    }

    public static class Params_170 extends _Params {
        private String arg1;
        private double arg2;
        private String arg3;

        public Params_170(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, double arg2, String arg3) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
        }

        public String getArg1() {
            return arg1;
        }

        public double getArg2() {
            return arg2;
        }

        public String getArg3() {
            return arg3;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_170 build(String arg1, double arg2, String arg3) {
                return new Params_170(dbIdx, op, arg1, arg2, arg3);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            builder.append("arg3", arg3);
            return builder.toString();
        }
    }

    public static class Params_180 extends _Params {
        private String arg1;
        private double arg2;
        private String arg3;
        private ZAddParams arg4;

        public Params_180(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, double arg2, String arg3, ZAddParams arg4) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
            this.arg4 = arg4;

        }

        public String getArg1() {
            return arg1;
        }

        public double getArg2() {
            return arg2;
        }

        public String getArg3() {
            return arg3;
        }

        public ZAddParams getArg4() {
            return arg4;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_180 build(String arg1, double arg2, String arg3, ZAddParams arg4) {
                return new Params_180(dbIdx, op, arg1, arg2, arg3, arg4);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            builder.append("arg3", arg3);
            builder.append("arg4", arg4);
            return builder.toString();
        }
    }

    public static class Params_190 extends _Params {
        private String arg1;
        private double arg2;
        private String arg3;
        private ZIncrByParams arg4;

        public Params_190(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, double arg2, String arg3, ZIncrByParams arg4) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
            this.arg4 = arg4;
        }

        public String getArg1() {
            return arg1;
        }

        public double getArg2() {
            return arg2;
        }

        public String getArg3() {
            return arg3;
        }

        public ZIncrByParams getArg4() {
            return arg4;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_190 build(String arg1, double arg2, String arg3, ZIncrByParams arg4) {
                return new Params_190(dbIdx, op, arg1, arg2, arg3, arg4);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            builder.append("arg3", arg3);
            builder.append("arg4", arg4);
            return builder.toString();
        }
    }

    public static class Params_200 extends _Params {
        private String arg1;
        private int arg2;

        public Params_200(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, int arg2) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        public String getArg1() {
            return arg1;
        }

        public int getArg2() {
            return arg2;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_200 build(String arg1, int arg2) {
                return new Params_200(dbIdx, op, arg1, arg2);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            return builder.toString();
        }
    }

    public static class Params_210 extends _Params {
        private String arg1;
        private int arg2;
        private String arg3;

        public Params_210(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, int arg2, String arg3) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
        }

        public String getArg1() {
            return arg1;
        }

        public int getArg2() {
            return arg2;
        }

        public String getArg3() {
            return arg3;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_210 build(String arg1, int arg2, String arg3) {
                return new Params_210(dbIdx, op, arg1, arg2, arg3);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            builder.append("arg3", arg3);
            return builder.toString();
        }
    }

    public static class Params_220 extends _Params {
        private String arg1;
        private LIST_POSITION arg2;
        private String arg3;
        private String arg4;

        public Params_220(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, LIST_POSITION arg2, String arg3, String arg4) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
            this.arg4 = arg4;
        }

        public String getArg1() {
            return arg1;
        }

        public LIST_POSITION getArg2() {
            return arg2;
        }

        public String getArg3() {
            return arg3;
        }

        public String getArg4() {
            return arg4;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_220 build(String arg1, LIST_POSITION arg2, String arg3, String arg4) {
                return new Params_220(dbIdx, op, arg1, arg2, arg3, arg4);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            builder.append("arg3", arg3);
            builder.append("arg4", arg4);
            return builder.toString();
        }
    }

    public static class Params_230 extends _Params {
        private String arg1;
        private long arg2;

        public Params_230(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, long arg2) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        public String getArg1() {
            return arg1;
        }

        public long getArg2() {
            return arg2;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_230 build(String arg1, long arg2) {
                return new Params_230(dbIdx, op, arg1, arg2);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            return builder.toString();
        }
    }

    public static class Params_240 extends _Params {
        private String arg1;
        private long arg2;
        private boolean arg3;

        public Params_240(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, long arg2, boolean arg3) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
        }

        public String getArg1() {
            return arg1;
        }

        public long getArg2() {
            return arg2;
        }

        public boolean getArg3() {
            return arg3;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_240 build(String arg1, long arg2, boolean arg3) {
                return new Params_240(dbIdx, op, arg1, arg2, arg3);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            builder.append("arg3", arg3);
            return builder.toString();
        }
    }

    public static class Params_250 extends _Params {
        private String arg1;
        private long arg2;
        private long arg3;

        public Params_250(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, long arg2, long arg3) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
        }

        public String getArg1() {
            return arg1;
        }

        public long getArg2() {
            return arg2;
        }

        public long getArg3() {
            return arg3;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_250 build(String arg1, long arg2, long arg3) {
                return new Params_250(dbIdx, op, arg1, arg2, arg3);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            builder.append("arg3", arg3);
            return builder.toString();
        }
    }

    public static class Params_260 extends _Params {
        private String arg1;
        private long arg2;
        private String arg3;

        public Params_260(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, long arg2, String arg3) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
        }

        public String getArg1() {
            return arg1;
        }

        public long getArg2() {
            return arg2;
        }

        public String getArg3() {
            return arg3;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_260 build(String arg1, long arg2, String arg3) {
                return new Params_260(dbIdx, op, arg1, arg2, arg3);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            builder.append("arg3", arg3);
            return builder.toString();
        }
    }

    public static class Params_270 extends _Params {
        private String arg1;
        private Map<String, Double> arg2;

        public Params_270(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, Map<String, Double> arg2) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        public String getArg1() {
            return arg1;
        }

        public Map<String, Double> getArg2() {
            return arg2;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_270 build(String arg1, Map<String, Double> arg2) {
                return new Params_270(dbIdx, op, arg1, arg2);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            return builder.toString();
        }
    }

    public static class Params_280 extends _Params {
        private String arg1;
        private Map<String, Double> arg2;
        private ZAddParams arg3;

        public Params_280(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, Map<String, Double> arg2, ZAddParams arg3) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
        }

        public String getArg1() {
            return arg1;
        }

        public Map<String, Double> getArg2() {
            return arg2;
        }

        public ZAddParams getArg3() {
            return arg3;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_280 build(String arg1, Map<String, Double> arg2, ZAddParams arg3) {
                return new Params_280(dbIdx, op, arg1, arg2, arg3);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            builder.append("arg3", arg3);
            return builder.toString();
        }
    }

    public static class Params_290 extends _Params {
        private String arg1;
        private Map<String, String> arg2;

        public Params_290(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, Map<String, String> arg2) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        public String getArg1() {
            return arg1;
        }

        public Map<String, String> getArg2() {
            return arg2;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_290 build(String arg1, Map<String, String> arg2) {
                return new Params_290(dbIdx, op, arg1, arg2);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            return builder.toString();
        }
    }

    public static class Params_300 extends _Params {
        private String arg1;
        private SortingParams arg2;

        public Params_300(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, SortingParams arg2) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        public String getArg1() {
            return arg1;
        }

        public SortingParams getArg2() {
            return arg2;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_300 build(String arg1, SortingParams arg2) {
                return new Params_300(dbIdx, op, arg1, arg2);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            return builder.toString();
        }
    }

    public static class Params_310 extends _Params {
        private String arg1;
        private String arg2;

        public Params_310(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, String arg2) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        public String getArg1() {
            return arg1;
        }

        public String getArg2() {
            return arg2;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_310 build(String arg1, String arg2) {
                return new Params_310(dbIdx, op, arg1, arg2);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            return builder.toString();
        }
    }

    public static class Params_320 extends _Params {
        private String arg1;
        private String arg2;
        private double arg3;

        public Params_320(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, String arg2, double arg3) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
        }

        public String getArg1() {
            return arg1;
        }

        public String getArg2() {
            return arg2;
        }

        public double getArg3() {
            return arg3;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_320 build(String arg1, String arg2, double arg3) {
                return new Params_320(dbIdx, op, arg1, arg2, arg3);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            builder.append("arg3", arg3);
            return builder.toString();
        }
    }

    public static class Params_330 extends _Params {
        private String arg1;
        private String arg2;
        private long arg3;

        public Params_330(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, String arg2, long arg3) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
        }

        public String getArg1() {
            return arg1;
        }

        public String getArg2() {
            return arg2;
        }

        public long getArg3() {
            return arg3;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_330 build(String arg1, String arg2, long arg3) {
                return new Params_330(dbIdx, op, arg1, arg2, arg3);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            builder.append("arg3", arg3);
            return builder.toString();
        }
    }

    public static class Params_340 extends _Params {
        private String arg1;
        private String arg2;
        private String arg3;

        public Params_340(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, String arg2, String arg3) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
        }

        public String getArg1() {
            return arg1;
        }

        public String getArg2() {
            return arg2;
        }

        public String getArg3() {
            return arg3;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_340 build(String arg1, String arg2, String arg3) {
                return new Params_340(dbIdx, op, arg1, arg2, arg3);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            builder.append("arg3", arg3);
            return builder.toString();
        }
    }

    public static class Params_350 extends _Params {
        private String arg1;
        private String arg2;
        private String arg3;
        private int arg4;
        private int arg5;

        public Params_350(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, String arg2, String arg3, int arg4, int arg5) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
            this.arg4 = arg4;
            this.arg5 = arg5;
        }

        public String getArg1() {
            return arg1;
        }

        public String getArg2() {
            return arg2;
        }

        public String getArg3() {
            return arg3;
        }

        public int getArg4() {
            return arg4;
        }

        public int getArg5() {
            return arg5;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_350 build(String arg1, String arg2, String arg3, int arg4, int arg5) {
                return new Params_350(dbIdx, op, arg1, arg2, arg3, arg4, arg5);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            builder.append("arg3", arg3);
            builder.append("arg4", arg4);
            builder.append("arg5", arg5);
            return builder.toString();
        }
    }

    public static class Params_360 extends _Params {
        private String arg1;
        private String arg2;
        private String arg3;
        private String arg4;
        private long arg5;

        public Params_360(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, String arg2, String arg3, String arg4, long arg5) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
            this.arg4 = arg4;
            this.arg5 = arg5;
        }

        public String getArg1() {
            return arg1;
        }

        public String getArg2() {
            return arg2;
        }

        public String getArg3() {
            return arg3;
        }

        public String getArg4() {
            return arg4;
        }

        public long getArg5() {
            return arg5;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_360 build(String arg1, String arg2, String arg3, String arg4, long arg5) {
                return new Params_360(dbIdx, op, arg1, arg2, arg3, arg4, arg5);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            builder.append("arg3", arg3);
            builder.append("arg4", arg4);
            builder.append("arg5", arg5);
            return builder.toString();
        }
    }

    public static class Params_370 extends _Params {
        private String arg1;

        private String[] arg2;

        public Params_370(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op, String arg1, String... arg2) {
            super(dbIdx, op);
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        public String getArg1() {
            return arg1;
        }

        public String[] getArg2() {
            return arg2;
        }

        public static class Builder extends AbstractBuilder {
            public Builder(int dbIdx, RedisDataStructureOp<? extends AbstractBuilder, ?> op) {
                super(dbIdx, op);
            }

            public Params_370 build(String arg1, String... arg2) {
                return new Params_370(dbIdx, op, arg1, arg2);
            }
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            builder.append("arg1", arg1);
            builder.append("arg2", arg2);
            return builder.toString();
        }
    }

}