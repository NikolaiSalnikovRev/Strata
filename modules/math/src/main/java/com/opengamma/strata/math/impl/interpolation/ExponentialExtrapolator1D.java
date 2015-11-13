/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.math.impl.interpolation;

import java.io.Serializable;
import java.util.Set;

import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.Property;
import org.joda.beans.impl.light.LightMetaBean;

import com.opengamma.strata.basics.interpolator.CurveExtrapolator;
import com.opengamma.strata.collect.ArgChecker;
import com.opengamma.strata.math.impl.interpolation.data.Interpolator1DDataBundle;

/**
 * Extrapolator based on a exponential function. Outside the data range the function is
 * an exponential exp(m*x) where m is such that:
 *  - on the left: exp(m * data.firstKey()) = data.firstValue()
 *  - on the right: exp(m * data.lastKey()) = data.lastValue()
 */
@BeanDefinition(style = "light", constructorScope = "public")
public final class ExponentialExtrapolator1D
    implements CurveExtrapolator, Extrapolator1D, ImmutableBean, Serializable {

  /** The extrapolator name. */
  public static final String NAME = "Exponential";

  //-------------------------------------------------------------------------
  @Override
  public Double extrapolate(Interpolator1DDataBundle data, Double value, Interpolator1D interpolator) {
    JodaBeanUtils.notNull(data, "data");
    JodaBeanUtils.notNull(value, "value");
    if (value < data.firstKey()) {
      return leftExtrapolate(data, value);
    } else if (value > data.lastKey()) {
      return rightExtrapolate(data, value);
    }
    throw new IllegalArgumentException("Value " + value + " was within data range");
  }

  @Override
  public double firstDerivative(Interpolator1DDataBundle data, Double value, Interpolator1D interpolator) {
    ArgChecker.notNull(data, "data");
    ArgChecker.notNull(value, "value");
    if (value < data.firstKey()) {
      return leftExtrapolateDerivative(data, value);
    } else if (value > data.lastKey()) {
      return rightExtrapolateDerivative(data, value);
    }
    throw new IllegalArgumentException("Value " + value + " was within data range");
  }

  @Override
  public double[] getNodeSensitivitiesForValue(Interpolator1DDataBundle data, Double value, Interpolator1D interpolator) {
    ArgChecker.notNull(data, "data");
    if (value < data.firstKey()) {
      return getLeftSensitivities(data, value);
    } else if (value > data.lastKey()) {
      return getRightSensitivities(data, value);
    }
    throw new IllegalArgumentException("Value " + value + " was within data range");
  }

  @Override
  public String getName() {
    return NAME;
  }

  private Double leftExtrapolate(Interpolator1DDataBundle data, Double value) {
    ArgChecker.notNull(data, "data");
    ArgChecker.notNull(value, "value");
    double x = data.firstKey();
    double y = data.firstValue();
    double m = Math.log(y) / x;
    return Math.exp(m * value);
  }

  private Double rightExtrapolate(Interpolator1DDataBundle data, Double value) {
    ArgChecker.notNull(data, "data");
    ArgChecker.notNull(value, "value");
    double x = data.lastKey();
    double y = data.lastValue();
    double m = Math.log(y) / x;
    return Math.exp(m * value);
  }

  private Double leftExtrapolateDerivative(Interpolator1DDataBundle data, Double value) {
    ArgChecker.notNull(data, "data");
    ArgChecker.notNull(value, "value");
    double x = data.firstKey();
    double y = data.firstValue();
    double m = Math.log(y) / x;
    return m * Math.exp(m * value);
  }

  private Double rightExtrapolateDerivative(Interpolator1DDataBundle data, Double value) {
    ArgChecker.notNull(data, "data");
    ArgChecker.notNull(value, "value");
    double x = data.lastKey();
    double y = data.lastValue();
    double m = Math.log(y) / x;
    return m * Math.exp(m * value);
  }

  private double[] getLeftSensitivities(Interpolator1DDataBundle data, double value) {
    ArgChecker.notNull(data, "data");
    ArgChecker.notNull(value, "value");
    double x = data.firstKey();
    double y = data.firstValue();
    double m = Math.log(y) / x;
    double ex = Math.exp(m * value);
    double[] result = new double[data.size()];
    result[0] = ex * value / (x * y);
    return result;
  }

  private double[] getRightSensitivities(Interpolator1DDataBundle data, Double value) {
    ArgChecker.notNull(data, "data");
    ArgChecker.notNull(value, "value");
    double x = data.lastKey();
    double y = data.lastValue();
    double m = Math.log(y) / x;
    double ex = Math.exp(m * value);
    double[] result = new double[data.size()];
    result[data.size() - 1] = ex * value / (x * y);
    return result;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code ExponentialExtrapolator1D}.
   */
  private static MetaBean META_BEAN = LightMetaBean.of(ExponentialExtrapolator1D.class);

  /**
   * The meta-bean for {@code ExponentialExtrapolator1D}.
   * @return the meta-bean, not null
   */
  public static MetaBean meta() {
    return META_BEAN;
  }

  static {
    JodaBeanUtils.registerMetaBean(META_BEAN);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Creates an instance.
   */
  public ExponentialExtrapolator1D() {
  }

  @Override
  public MetaBean metaBean() {
    return META_BEAN;
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
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(32);
    buf.append("ExponentialExtrapolator1D{");
    buf.append('}');
    return buf.toString();
  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
