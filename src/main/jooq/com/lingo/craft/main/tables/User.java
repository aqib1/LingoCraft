/*
 * This file is generated by jOOQ.
 */
package com.lingo.craft.main.tables;


import com.lingo.craft.main.Keys;
import com.lingo.craft.main.Public;
import com.lingo.craft.main.tables.records.UserRecord;

import java.util.UUID;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function6;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row6;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class User extends TableImpl<UserRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.user</code>
     */
    public static final User USER = new User();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserRecord> getRecordType() {
        return UserRecord.class;
    }

    /**
     * The column <code>public.user.id</code>.
     */
    public final TableField<UserRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false).defaultValue(DSL.field(DSL.raw("gen_random_uuid()"), SQLDataType.UUID)), this, "");

    /**
     * The column <code>public.user.name</code>.
     */
    public final TableField<UserRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.user.password</code>.
     */
    public final TableField<UserRecord, String> PASSWORD = createField(DSL.name("password"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.user.email</code>.
     */
    public final TableField<UserRecord, String> EMAIL = createField(DSL.name("email"), SQLDataType.VARCHAR(320).nullable(false), this, "");

    /**
     * The column <code>public.user.phone</code>.
     */
    public final TableField<UserRecord, String> PHONE = createField(DSL.name("phone"), SQLDataType.VARCHAR(15).nullable(false), this, "");

    /**
     * The column <code>public.user.roleid</code>.
     */
    public final TableField<UserRecord, Integer> ROLEID = createField(DSL.name("roleid"), SQLDataType.INTEGER.nullable(false), this, "");

    private User(Name alias, Table<UserRecord> aliased) {
        this(alias, aliased, null);
    }

    private User(Name alias, Table<UserRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.user</code> table reference
     */
    public User(String alias) {
        this(DSL.name(alias), USER);
    }

    /**
     * Create an aliased <code>public.user</code> table reference
     */
    public User(Name alias) {
        this(alias, USER);
    }

    /**
     * Create a <code>public.user</code> table reference
     */
    public User() {
        this(DSL.name("user"), null);
    }

    public <O extends Record> User(Table<O> child, ForeignKey<O, UserRecord> key) {
        super(child, key, USER);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<UserRecord> getPrimaryKey() {
        return Keys.USER_PKEY;
    }

    @Override
    public User as(String alias) {
        return new User(DSL.name(alias), this);
    }

    @Override
    public User as(Name alias) {
        return new User(alias, this);
    }

    @Override
    public User as(Table<?> alias) {
        return new User(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(String name) {
        return new User(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(Name name) {
        return new User(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(Table<?> name) {
        return new User(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row6<UUID, String, String, String, String, Integer> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function6<? super UUID, ? super String, ? super String, ? super String, ? super String, ? super Integer, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function6<? super UUID, ? super String, ? super String, ? super String, ? super String, ? super Integer, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
