/*
 * Copyright 2010-2011 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.ning.billing.catalog;

import java.math.BigDecimal;
import java.util.Date;

import com.ning.billing.catalog.api.Currency;

public class MockInternationalPrice extends InternationalPrice {
	
	MockInternationalPrice() {
		setEffectiveDateForExistingSubscriptons(new Date());
		setPrices(new Price[] {
			new Price().setCurrency(Currency.USD).setValue(new BigDecimal(1))	
		});
	}
	
	MockInternationalPrice(Date effectiveDateForExistingSubscriptions, Price[] price) {
		setEffectiveDateForExistingSubscriptons(effectiveDateForExistingSubscriptions);
		setPrices(price);
	}

	MockInternationalPrice(Price... price) {
		setEffectiveDateForExistingSubscriptons(new Date());
		setPrices(price);
	}

}
