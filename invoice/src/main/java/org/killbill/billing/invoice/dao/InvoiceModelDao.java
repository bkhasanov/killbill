/*
 * Copyright 2010-2013 Ning, Inc.
 * Copyright 2014-2016 Groupon, Inc
 * Copyright 2014-2016 The Billing Project, LLC
 *
 * The Billing Project licenses this file to you under the Apache License, version 2.0
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

package org.killbill.billing.invoice.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import org.killbill.billing.catalog.api.Currency;
import org.killbill.billing.invoice.api.Invoice;
import org.killbill.billing.invoice.api.InvoiceStatus;
import org.killbill.billing.util.UUIDs;
import org.killbill.billing.util.dao.TableName;
import org.killbill.billing.util.entity.dao.EntityModelDao;
import org.killbill.billing.util.entity.dao.EntityModelDaoBase;

public class InvoiceModelDao extends EntityModelDaoBase implements EntityModelDao<Invoice> {

    private UUID accountId;
    private Integer invoiceNumber;
    private LocalDate invoiceDate;
    private LocalDate targetDate;
    private Currency currency;
    private boolean migrated;
    private InvoiceStatus status;
    private boolean parentInvoice;

    // Not in the database, for convenience only
    private List<InvoiceItemModelDao> invoiceItems = new LinkedList<InvoiceItemModelDao>();
    private List<InvoicePaymentModelDao> invoicePayments = new LinkedList<InvoicePaymentModelDao>();
    private Currency processedCurrency;

    private boolean isWrittenOff;

    public InvoiceModelDao() { /* For the DAO mapper */ }

    public InvoiceModelDao(final UUID id, @Nullable final DateTime createdDate, final UUID accountId,
                           @Nullable final Integer invoiceNumber, final LocalDate invoiceDate, final LocalDate targetDate,
                           final Currency currency, final boolean migrated, final InvoiceStatus status, final boolean parentInvoice) {
        super(id, createdDate, createdDate);
        this.accountId = accountId;
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.targetDate = targetDate;
        this.currency = currency;
        this.migrated = migrated;
        this.isWrittenOff = false;
        this.status = status;
        this.parentInvoice = parentInvoice;
    }

    public InvoiceModelDao(final UUID accountId, final LocalDate invoiceDate, final LocalDate targetDate, final Currency currency, final boolean migrated) {
        this(UUIDs.randomUUID(), null, accountId, null, invoiceDate, targetDate, currency, migrated, InvoiceStatus.COMMITTED, false);
    }

    public InvoiceModelDao(final UUID accountId, final LocalDate invoiceDate, final LocalDate targetDate, final Currency currency, final boolean migrated, final InvoiceStatus status) {
        this(UUIDs.randomUUID(), null, accountId, null, invoiceDate, targetDate, currency, migrated, status, false);
    }

    public InvoiceModelDao(final UUID accountId, final LocalDate invoiceDate, final LocalDate targetDate, final Currency currency) {
        this(UUIDs.randomUUID(), null, accountId, null, invoiceDate, targetDate, currency, false, InvoiceStatus.COMMITTED, false);
    }

    public InvoiceModelDao(final UUID accountId, final LocalDate invoiceDate, final Currency currency, final InvoiceStatus status, final boolean isParentInvoice) {
        this(UUIDs.randomUUID(), null, accountId, null, invoiceDate, null, currency, false, status, isParentInvoice);
    }

    public InvoiceModelDao(final Invoice invoice) {
        this(invoice.getId(), invoice.getCreatedDate(), invoice.getAccountId(), invoice.getInvoiceNumber(), invoice.getInvoiceDate(),
             invoice.getTargetDate(), invoice.getCurrency(), invoice.isMigrationInvoice(), invoice.getStatus(), invoice.isParentInvoice());
    }

    public void addInvoiceItems(final List<InvoiceItemModelDao> invoiceItems) {
        this.invoiceItems.addAll(invoiceItems);
    }

    public void addInvoiceItem(final InvoiceItemModelDao invoiceItem) {
        this.invoiceItems.add(invoiceItem);
    }

    public List<InvoiceItemModelDao> getInvoiceItems() {
        return invoiceItems;
    }

    public void addPayments(final List<InvoicePaymentModelDao> invoicePayments) {
        this.invoicePayments.addAll(invoicePayments);
    }

    public List<InvoicePaymentModelDao> getInvoicePayments() {
        return invoicePayments;
    }

    public void setProcessedCurrency(Currency currency) {
        this.processedCurrency = currency;
    }

    public Currency getProcessedCurrency() {
        return processedCurrency != null ? processedCurrency : currency;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public boolean isMigrated() {
        return migrated;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public boolean isParentInvoice() {
        return parentInvoice;
    }

    public void setAccountId(final UUID accountId) {
        this.accountId = accountId;
    }

    public void setInvoiceNumber(final Integer invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setInvoiceDate(final LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setTargetDate(final LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public void setMigrated(final boolean migrated) {
        this.migrated = migrated;
    }

    public void setInvoiceItems(final List<InvoiceItemModelDao> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public void setInvoicePayments(final List<InvoicePaymentModelDao> invoicePayments) {
        this.invoicePayments = invoicePayments;
    }

    public boolean isWrittenOff() {
        return isWrittenOff;
    }

    public void setIsWrittenOff(final boolean isWrittenOff) {
        this.isWrittenOff = isWrittenOff;
    }

    public void setStatus(final InvoiceStatus status) {
        this.status = status;
    }

    public void setParentInvoice(final boolean parentInvoice) {
        this.parentInvoice = parentInvoice;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InvoiceModelDao{");
        sb.append("accountId=").append(accountId);
        sb.append(", invoiceNumber=").append(invoiceNumber);
        sb.append(", invoiceDate=").append(invoiceDate);
        sb.append(", targetDate=").append(targetDate);
        sb.append(", currency=").append(currency);
        sb.append(", migrated=").append(migrated);
        sb.append(", status=").append(status);
        sb.append(", invoiceItems=").append(invoiceItems);
        sb.append(", invoicePayments=").append(invoicePayments);
        sb.append(", processedCurrency=").append(processedCurrency);
        sb.append(", isWrittenOff=").append(isWrittenOff);
        sb.append(", parentInvoice=").append(parentInvoice);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        final InvoiceModelDao that = (InvoiceModelDao) o;

        if (migrated != that.migrated) {
            return false;
        }
        if (isWrittenOff != that.isWrittenOff) {
            return false;
        }
        if (accountId != null ? !accountId.equals(that.accountId) : that.accountId != null) {
            return false;
        }
        if (invoiceNumber != null ? !invoiceNumber.equals(that.invoiceNumber) : that.invoiceNumber != null) {
            return false;
        }
        if (invoiceDate != null ? invoiceDate.compareTo(that.invoiceDate) != 0 : that.invoiceDate != null) {
            return false;
        }
        if (targetDate != null ? targetDate.compareTo(that.targetDate) != 0 : that.targetDate != null) {
            return false;
        }
        if (currency != that.currency) {
            return false;
        }
        if (status != that.status) {
            return false;
        }
        if (invoiceItems != null ? !invoiceItems.equals(that.invoiceItems) : that.invoiceItems != null) {
            return false;
        }
        if (invoicePayments != null ? !invoicePayments.equals(that.invoicePayments) : that.invoicePayments != null) {
            return false;
        }
        if (parentInvoice != that.parentInvoice) {
            return false;
        }
        return processedCurrency == that.processedCurrency;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        result = 31 * result + (invoiceNumber != null ? invoiceNumber.hashCode() : 0);
        result = 31 * result + (invoiceDate != null ? invoiceDate.hashCode() : 0);
        result = 31 * result + (targetDate != null ? targetDate.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (migrated ? 1 : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (invoiceItems != null ? invoiceItems.hashCode() : 0);
        result = 31 * result + (invoicePayments != null ? invoicePayments.hashCode() : 0);
        result = 31 * result + (processedCurrency != null ? processedCurrency.hashCode() : 0);
        result = 31 * result + (isWrittenOff ? 1 : 0);
        result = 31 * result + (parentInvoice ? 1 : 0);
        return result;
    }

    @Override
    public TableName getTableName() {
        return TableName.INVOICES;
    }

    @Override
    public TableName getHistoryTableName() {
        return null;
    }

}
