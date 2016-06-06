/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.pricer.swaption;

import com.opengamma.strata.market.param.ParameterPerturbation;
import com.opengamma.strata.market.product.swaption.SwaptionVolatilities;

/**
 * Volatility for swaptions in the log-normal or Black model.
 */
public interface BlackSwaptionVolatilities
    extends SwaptionVolatilities {

  @Override
  public abstract BlackSwaptionVolatilities withParameter(int parameterIndex, double newValue);

  @Override
  public abstract BlackSwaptionVolatilities withPerturbation(ParameterPerturbation perturbation);

}
