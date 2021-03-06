/*
 * Copyright 2018 NEM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.nem.sdk.infrastructure;


import io.nem.sdk.model.transaction.Transaction;
import io.nem.sdk.model.transaction.TransactionStatus;
import io.nem.sdk.model.transaction.TransactionType;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionHttpTest extends BaseTest {
    private TransactionHttp transactionHttp;
    private final String transactionHash = "EE5B39DBDA00BA39D06B9E67AE5B43162366C862D9B8F656F7E7068D327377BE";

    @Before
    public void setup() throws IOException {
        transactionHttp = new TransactionHttp(this.getNodeUrl());
    }

    @Test
    public void getTransaction() throws ExecutionException, InterruptedException {
        Transaction transaction = transactionHttp
                .getTransaction(transactionHash)
                .toFuture()
                .get();

        assertEquals(TransactionType.TRANSFER.getValue(), transaction.getType().getValue());
        assertEquals(transactionHash, transaction.getTransactionInfo().get().getHash().get());
    }

    @Test
    public void getTransactions() throws ExecutionException, InterruptedException {
        List<Transaction> transaction = transactionHttp
                .getTransactions(Collections.singletonList(transactionHash))
                .toFuture()
                .get();

        assertEquals(TransactionType.TRANSFER.getValue(), transaction.get(0).getType().getValue());
        assertEquals(transactionHash, transaction.get(0).getTransactionInfo().get().getHash().get());
    }

    @Test
    public void getTransactionStatus() throws ExecutionException, InterruptedException {
        TransactionStatus transactionStatus = transactionHttp
                .getTransactionStatus(transactionHash)
                .toFuture()
                .get();

        assertEquals(transactionHash, transactionStatus.getHash());
    }

    @Test
    public void getTransactionsStatuses() throws ExecutionException, InterruptedException {
        List<TransactionStatus> transactionStatuses = transactionHttp
                .getTransactionStatuses(Collections.singletonList(transactionHash))
                .toFuture()
                .get();

        assertEquals(transactionHash, transactionStatuses.get(0).getHash());
    }
}