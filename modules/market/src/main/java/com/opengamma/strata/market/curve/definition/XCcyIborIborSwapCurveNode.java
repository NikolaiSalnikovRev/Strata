/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.market.curve.definition;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
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

import com.google.common.collect.ImmutableSet;
import com.opengamma.strata.basics.BuySell;
import com.opengamma.strata.basics.market.ObservableKey;
import com.opengamma.strata.basics.market.ObservableValues;
import com.opengamma.strata.market.curve.DatedCurveParameterMetadata;
import com.opengamma.strata.market.curve.TenorCurveNodeMetadata;
import com.opengamma.strata.market.value.ValueType;
import com.opengamma.strata.product.rate.swap.SwapTrade;
import com.opengamma.strata.product.rate.swap.type.XCcyIborIborSwapTemplate;

/**
 * A curve node whose instrument is a cross-currency Ibor-Ibor interest rate swap.
 * <p>
 * Two market quotes are required, one for the spread and one for the FX rate.
 */
@BeanDefinition
public final class XCcyIborIborSwapCurveNode
    implements CurveNode, ImmutableBean, Serializable {

  /**
   * The template for the swap associated with this node.
   */
  @PropertyDefinition(validate = "notNull")
  private final XCcyIborIborSwapTemplate template;
  /**
   * The key identifying the market data value which provides the spread.
   */
  @PropertyDefinition(validate = "notNull")
  private final ObservableKey spreadKey;
  /**
   * The key identifying the market data value which provides the FX rate.
   */
  @PropertyDefinition(validate = "notNull")
  private final ObservableKey fxKey;
  /**
   * The spread added to the spread market quote.
   */
  @PropertyDefinition
  private final double spread;

  //-------------------------------------------------------------------------
  /**
   * Returns a curve node for a cross-currency Ibor-Ibor interest rate swap using the
   * specified instrument template and rate.
   *
   * @param template  the template used for building the instrument for the node
   * @param spreadKey  the key identifying the market spread used when building the instrument for the node
   * @param fxKey  the key identifying the FX rate for the near date used when building the instrument for the node
   * @return a node whose instrument is built from the template using a market rate
   */
  public static XCcyIborIborSwapCurveNode of(
      XCcyIborIborSwapTemplate template,
      ObservableKey spreadKey,
      ObservableKey fxKey) {

    return XCcyIborIborSwapCurveNode.builder()
        .template(template).spreadKey(spreadKey).fxKey(fxKey).spread(0.0d).build();
  }

  /**
   * Returns a curve node for a cross-currency Ibor-Ibor interest rate swap using the
   * specified instrument template, rate key and spread.
   *
   * @param template  the template defining the node instrument
   * @param spreadKey  the key identifying the market spread used when building the instrument for the node
   * @param fxKey  the key identifying the FX rate for the near date used when building the instrument for the node
   * @param spread  the spread amount added to the rate
   * @return a node whose instrument is built from the template using a market rate
   */
  public static XCcyIborIborSwapCurveNode of(
      XCcyIborIborSwapTemplate template,
      ObservableKey spreadKey,
      ObservableKey fxKey,
      double spread) {

    return XCcyIborIborSwapCurveNode.builder()
        .template(template).spreadKey(spreadKey).fxKey(fxKey).spread(spread).build();
  }

  //-------------------------------------------------------------------------
  @Override
  public Set<ObservableKey> requirements() {
    return ImmutableSet.of(spreadKey, fxKey);
  }

  @Override
  public DatedCurveParameterMetadata metadata(LocalDate valuationDate) {
    SwapTrade trade = template.toTrade(valuationDate, BuySell.BUY, 1, 1, 0);
    return TenorCurveNodeMetadata.of(trade.getProduct().getEndDate(), template.getTenor());
  }

  @Override
  public SwapTrade trade(LocalDate valuationDate, ObservableValues marketData) {
    double marketQuote = marketData.getValue(spreadKey) + spread;
    double fxRate = marketData.getValue(fxKey);
    return template.toTrade(valuationDate, BuySell.BUY, 1, fxRate, marketQuote);
  }

  @Override
  public double initialGuess(LocalDate valuationDate, ObservableValues marketData, ValueType valueType) {
    if (ValueType.DISCOUNT_FACTOR.equals(valueType)) {
      return 1.0d;
    }
    return 0.0d;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code XCcyIborIborSwapCurveNode}.
   * @return the meta-bean, not null
   */
  public static XCcyIborIborSwapCurveNode.Meta meta() {
    return XCcyIborIborSwapCurveNode.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(XCcyIborIborSwapCurveNode.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static XCcyIborIborSwapCurveNode.Builder builder() {
    return new XCcyIborIborSwapCurveNode.Builder();
  }

  private XCcyIborIborSwapCurveNode(
      XCcyIborIborSwapTemplate template,
      ObservableKey spreadKey,
      ObservableKey fxKey,
      double spread) {
    JodaBeanUtils.notNull(template, "template");
    JodaBeanUtils.notNull(spreadKey, "spreadKey");
    JodaBeanUtils.notNull(fxKey, "fxKey");
    this.template = template;
    this.spreadKey = spreadKey;
    this.fxKey = fxKey;
    this.spread = spread;
  }

  @Override
  public XCcyIborIborSwapCurveNode.Meta metaBean() {
    return XCcyIborIborSwapCurveNode.Meta.INSTANCE;
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
   * Gets the template for the swap associated with this node.
   * @return the value of the property, not null
   */
  public XCcyIborIborSwapTemplate getTemplate() {
    return template;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the key identifying the market data value which provides the spread.
   * @return the value of the property, not null
   */
  public ObservableKey getSpreadKey() {
    return spreadKey;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the key identifying the market data value which provides the FX rate.
   * @return the value of the property, not null
   */
  public ObservableKey getFxKey() {
    return fxKey;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the spread added to the spread market quote.
   * @return the value of the property
   */
  public double getSpread() {
    return spread;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      XCcyIborIborSwapCurveNode other = (XCcyIborIborSwapCurveNode) obj;
      return JodaBeanUtils.equal(template, other.template) &&
          JodaBeanUtils.equal(spreadKey, other.spreadKey) &&
          JodaBeanUtils.equal(fxKey, other.fxKey) &&
          JodaBeanUtils.equal(spread, other.spread);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(template);
    hash = hash * 31 + JodaBeanUtils.hashCode(spreadKey);
    hash = hash * 31 + JodaBeanUtils.hashCode(fxKey);
    hash = hash * 31 + JodaBeanUtils.hashCode(spread);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(160);
    buf.append("XCcyIborIborSwapCurveNode{");
    buf.append("template").append('=').append(template).append(',').append(' ');
    buf.append("spreadKey").append('=').append(spreadKey).append(',').append(' ');
    buf.append("fxKey").append('=').append(fxKey).append(',').append(' ');
    buf.append("spread").append('=').append(JodaBeanUtils.toString(spread));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code XCcyIborIborSwapCurveNode}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code template} property.
     */
    private final MetaProperty<XCcyIborIborSwapTemplate> template = DirectMetaProperty.ofImmutable(
        this, "template", XCcyIborIborSwapCurveNode.class, XCcyIborIborSwapTemplate.class);
    /**
     * The meta-property for the {@code spreadKey} property.
     */
    private final MetaProperty<ObservableKey> spreadKey = DirectMetaProperty.ofImmutable(
        this, "spreadKey", XCcyIborIborSwapCurveNode.class, ObservableKey.class);
    /**
     * The meta-property for the {@code fxKey} property.
     */
    private final MetaProperty<ObservableKey> fxKey = DirectMetaProperty.ofImmutable(
        this, "fxKey", XCcyIborIborSwapCurveNode.class, ObservableKey.class);
    /**
     * The meta-property for the {@code spread} property.
     */
    private final MetaProperty<Double> spread = DirectMetaProperty.ofImmutable(
        this, "spread", XCcyIborIborSwapCurveNode.class, Double.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "template",
        "spreadKey",
        "fxKey",
        "spread");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1321546630:  // template
          return template;
        case 1302780908:  // spreadKey
          return spreadKey;
        case 97849389:  // fxKey
          return fxKey;
        case -895684237:  // spread
          return spread;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public XCcyIborIborSwapCurveNode.Builder builder() {
      return new XCcyIborIborSwapCurveNode.Builder();
    }

    @Override
    public Class<? extends XCcyIborIborSwapCurveNode> beanType() {
      return XCcyIborIborSwapCurveNode.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code template} property.
     * @return the meta-property, not null
     */
    public MetaProperty<XCcyIborIborSwapTemplate> template() {
      return template;
    }

    /**
     * The meta-property for the {@code spreadKey} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ObservableKey> spreadKey() {
      return spreadKey;
    }

    /**
     * The meta-property for the {@code fxKey} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ObservableKey> fxKey() {
      return fxKey;
    }

    /**
     * The meta-property for the {@code spread} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Double> spread() {
      return spread;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -1321546630:  // template
          return ((XCcyIborIborSwapCurveNode) bean).getTemplate();
        case 1302780908:  // spreadKey
          return ((XCcyIborIborSwapCurveNode) bean).getSpreadKey();
        case 97849389:  // fxKey
          return ((XCcyIborIborSwapCurveNode) bean).getFxKey();
        case -895684237:  // spread
          return ((XCcyIborIborSwapCurveNode) bean).getSpread();
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
   * The bean-builder for {@code XCcyIborIborSwapCurveNode}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<XCcyIborIborSwapCurveNode> {

    private XCcyIborIborSwapTemplate template;
    private ObservableKey spreadKey;
    private ObservableKey fxKey;
    private double spread;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(XCcyIborIborSwapCurveNode beanToCopy) {
      this.template = beanToCopy.getTemplate();
      this.spreadKey = beanToCopy.getSpreadKey();
      this.fxKey = beanToCopy.getFxKey();
      this.spread = beanToCopy.getSpread();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1321546630:  // template
          return template;
        case 1302780908:  // spreadKey
          return spreadKey;
        case 97849389:  // fxKey
          return fxKey;
        case -895684237:  // spread
          return spread;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case -1321546630:  // template
          this.template = (XCcyIborIborSwapTemplate) newValue;
          break;
        case 1302780908:  // spreadKey
          this.spreadKey = (ObservableKey) newValue;
          break;
        case 97849389:  // fxKey
          this.fxKey = (ObservableKey) newValue;
          break;
        case -895684237:  // spread
          this.spread = (Double) newValue;
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
    public XCcyIborIborSwapCurveNode build() {
      return new XCcyIborIborSwapCurveNode(
          template,
          spreadKey,
          fxKey,
          spread);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the template for the swap associated with this node.
     * @param template  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder template(XCcyIborIborSwapTemplate template) {
      JodaBeanUtils.notNull(template, "template");
      this.template = template;
      return this;
    }

    /**
     * Sets the key identifying the market data value which provides the spread.
     * @param spreadKey  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder spreadKey(ObservableKey spreadKey) {
      JodaBeanUtils.notNull(spreadKey, "spreadKey");
      this.spreadKey = spreadKey;
      return this;
    }

    /**
     * Sets the key identifying the market data value which provides the FX rate.
     * @param fxKey  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder fxKey(ObservableKey fxKey) {
      JodaBeanUtils.notNull(fxKey, "fxKey");
      this.fxKey = fxKey;
      return this;
    }

    /**
     * Sets the spread added to the spread market quote.
     * @param spread  the new value
     * @return this, for chaining, not null
     */
    public Builder spread(double spread) {
      this.spread = spread;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(160);
      buf.append("XCcyIborIborSwapCurveNode.Builder{");
      buf.append("template").append('=').append(JodaBeanUtils.toString(template)).append(',').append(' ');
      buf.append("spreadKey").append('=').append(JodaBeanUtils.toString(spreadKey)).append(',').append(' ');
      buf.append("fxKey").append('=').append(JodaBeanUtils.toString(fxKey)).append(',').append(' ');
      buf.append("spread").append('=').append(JodaBeanUtils.toString(spread));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
