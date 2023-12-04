package org.example.ormdemo.repositories

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.example.ormdemo.entities.BaseEntity
import org.hibernate.Session
import org.jooq.DSLContext
import org.jooq.Table
import org.jooq.TableField
import org.jooq.UpdatableRecord
import org.jooq.conf.RenderNameCase
import org.jooq.conf.RenderQuotedNames
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.springframework.stereotype.Component
import java.lang.reflect.ParameterizedType

@Component
abstract class BaseRepository<T : BaseEntity, U : UpdatableRecord<*>>(private val table: Table<U>) {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    private fun getEntityClass(): Class<T> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
    }

    fun getById(id: Long): T? {
        return query {
            it.select()
                .from(table)
                .where(table.field("ID", Long::class.java)?.eq(id))
                .fetchOne()
                ?.into(getEntityClass())
        }
    }

    fun save(entity: T): T {
        var result = entity
        query {
            val record = it.newRecord(table, entity)
            if (entity.id != null) {
                it.executeUpdate(record)
            } else {
                record.store()
            }
            result = record.into(getEntityClass())
        }
        return result
    }

    protected fun <R> query(queryMethod: (context: DSLContext) -> R?): R? {
        val session = entityManager.unwrap(Session::class.java)
        var result: R? = null
        session.doWork { connection ->
            val settings = Settings()
                .withRenderQuotedNames(RenderQuotedNames.EXPLICIT_DEFAULT_UNQUOTED)
                .withRenderNameCase(RenderNameCase.LOWER_IF_UNQUOTED)
            val context = DSL.using(connection, settings)
            result = queryMethod(context)
        }
        return result
    }

}