package co.pala.payandgo.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Store {

    Store() {
    }

    public abstract String getName();

    public static Builder builder() {
        return new AutoValue_Store.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setName(String name);
        public abstract Store build();
    }
}
