package gl.linpeng.serverless.advisor.common;

import java.util.HashSet;
import java.util.Set;

/**
 * Aengine constant
 *
 * @author linpeng
 */
public final class Constants {

    public static final int COUNT_TYPE_PRICIPLE = 1;
    public static final int COUNT_TYPE_PRICIPLE_ITEM = 2;

    public enum UserFeatureType {
        DISEASE(1, "DISEASE"), FOOD(2, "FOOD"), INGREDIENT(3, "INGREDIENT"), TASTE(4, "TASTE"), COOKING(5, "COOKING");
        public String name;
        public Integer value;

        UserFeatureType(Integer value, String name) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }

    public enum PrincipleItemType {
        FOOD(1, "FOOD"), NATRIENT(2, "NATRIENT");

        public String name;
        public Integer value;

        PrincipleItemType(Integer value, String name) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

    }

    public enum Adverb {

        NO(1, "NO"), MORE(2, "MORE"), LESS(3, "LESS");

        public String name;
        public Integer value;

        Adverb(Integer value, String name) {
            this.name = name;
            this.value = value;
        }

        static Set<String> aliasOfAdverbNo = new HashSet<>();
        static Set<String> aliasOfAdverbMore = new HashSet<>();
        static Set<String> aliasOfAdverbLess = new HashSet<>();

        static {
            aliasOfAdverbNo.add("不");
            aliasOfAdverbNo.add("忌");
            aliasOfAdverbNo.add("禁");

            aliasOfAdverbMore.add("多");
            aliasOfAdverbMore.add("宜");

            aliasOfAdverbLess.add("少");
            aliasOfAdverbLess.add("低");
        }

        public static Adverb adverbValueOf(String text) {
            if (aliasOfAdverbNo.contains(text)) {
                return Adverb.NO;
            } else if (aliasOfAdverbMore.contains(text)) {
                return Adverb.MORE;
            } else if (aliasOfAdverbLess.contains(text)) {
                return Adverb.LESS;
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

    }

    public enum OperationLogType {

        DOWN(0, "DOWN"), UP(1, "UP"), VISIT(2, "VISIT"), SHARE(3, "SHARE");

        public String name;
        public Integer value;

        OperationLogType(Integer value, String name) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

    }

    public enum OperationLogTargetType {

        PRINCIPLE(1, "PRINCIPLE"), ARTICLE(2, "ARTICLE"), FOOD(3, "FOOD");

        public String name;
        public Integer value;

        OperationLogTargetType(Integer value, String name) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

    }

    public enum OperationLogSourceType {

        HUMAN(1, "HUMAN"), ARTICLE(2, "ARTICLE");

        public String name;
        public Integer value;

        OperationLogSourceType(Integer value, String name) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

    }

}
