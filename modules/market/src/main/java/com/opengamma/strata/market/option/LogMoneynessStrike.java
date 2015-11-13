/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.strata.market.option;

import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.strata.collect.ArgChecker;

/**
 * A strike based on log-moneyness. 
 * <p>
 * The log-moneyness is defined as {@code ln(strike/forward)}. 
 * The strike value and forward value should strictly positive. 
 */
@BeanDefinition(builderScope = "private")
public final class LogMoneynessStrike
    implements Strike, ImmutableBean, Serializable {

  /**
   * The value of log-moneyness. 
   */
  @PropertyDefinition(overrideGet = true)
  private final double value;

  /**
   * Obtains an instance of {@code LogMoneyness} with the value of log-moneyness.
   * 
   * @param logMoneyness  the value of log-moneyness
   * @return the instance
   */
  public static LogMoneynessStrike of(double logMoneyness) {
    return new LogMoneynessStrike(logMoneyness);
  }

  /**
   * Obtains an instance of {@code LogMoneyness} from the strike and forward.
   * <p>
   * The log-moneyness is defined as {@code ln(strike/forward)}. 
   * 
   * @param strike  the strike, not negative
   * @param forward  the forward, not negative
   * @return the instance
   */
  public static LogMoneynessStrike ofStrikeAndForward(double strike, double forward) {
    return of(Math.log(ArgChecker.notNegative(strike, "strike") / ArgChecker.notNegative(forward, "forward")));
  }

  //-------------------------------------------------------------------------
  @Override
  public StrikeType getType() {
    return StrikeType.LOG_MONEYNESS;
  }

  @Override
  public Strike withValue(double value) {
    return new LogMoneynessStrike(value);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code LogMoneynessStrike}.
   * @return the meta-bean, not null
   */
  public static LogMoneynessStrike.Meta meta() {
    return LogMoneynessStrike.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(LogMoneynessStrike.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  private LogMoneynessStrike(
      double value) {
    this.value = value;
  }

  @Override
  public LogMoneynessStrike.Meta metaBean() {
    return LogMoneynessStrike.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the value of log-moneyness.
   * @return the value of the property
   */
  @Override
  public double getValue() {
    return value;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      LogMoneynessStrike other = (LogMoneynessStrike) obj;
      return JodaBeanUtils.equal(value, other.value);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(value);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append("LogMoneynessStrike{");
    buf.append("value").append('=').append(JodaBeanUtils.toString(value));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code LogMoneynessStrike}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code value} property.
     */
    private final MetaProperty<Double> value = DirectMetaProperty.ofImmutable(
        this, "value", LogMoneynessStrike.class, Double.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "value");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 111972721:  // value
          return value;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends LogMoneynessStrike> builder() {
      return new LogMoneynessStrike.Builder();
    }

    @Override
    public Class<? extends LogMoneynessStrike> beanType() {
      return LogMoneynessStrike.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code value} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Double> value() {
      return value;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 111972721:  // value
          return ((LogMoneynessStrike) bean).getValue();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code LogMoneynessStrike}.
   */
  private static final class Builder extends DirectFieldsBeanBuilder<LogMoneynessStrike> {

    private double value;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 111972721:  // value
          return value;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 111972721:  // value
          this.value = (Double) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public LogMoneynessStrike build() {
      return new LogMoneynessStrike(
          value);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(64);
      buf.append("LogMoneynessStrike.Builder{");
      buf.append("value").append('=').append(JodaBeanUtils.toString(value));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
