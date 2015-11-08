package de.drippinger.config;

import org.jooq.TransactionContext;
import org.jooq.TransactionProvider;
import org.jooq.tools.JooqLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;

import static org.springframework.transaction.TransactionDefinition.PROPAGATION_NESTED;


/**
 * An example <code>TransactionProvider</code> implementing the {@link TransactionProvider} contract for use with
 * Spring.
 *
 * @author Lukas Eder
 */
public class SpringTransactionProvider implements TransactionProvider {

	private static final JooqLogger log = JooqLogger.getLogger(SpringTransactionProvider.class);

	@Resource
	DataSourceTransactionManager txMgr;

	@Override
	public void begin(TransactionContext ctx) {
		TransactionStatus tx = txMgr.getTransaction(new DefaultTransactionDefinition(PROPAGATION_NESTED));
		ctx.transaction(new SpringTransaction(tx));
	}

	@Override
	public void commit(TransactionContext ctx) {
		txMgr.commit(((SpringTransaction) ctx.transaction()).tx);
	}

	@Override
	public void rollback(TransactionContext ctx) {
		txMgr.rollback(((SpringTransaction) ctx.transaction()).tx);
	}
}
