package com.korotkov.creditCRM.dao.gettingClients;

import com.korotkov.creditCRM.model.clients.*;
import com.korotkov.creditCRM.model.clients.exportForUploadingNewLeads.ExportPassiveClients;
import com.korotkov.mainCurrentApp.model.helpedObjects.ClientAddressInsertCRM;
import com.korotkov.mainCurrentApp.model.helpedObjects.ClientPhoneInsertCRM;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Repository
public class ClientCRMDaoImpl implements ClientCRMDao {
    private static final Logger logger = LoggerFactory.getLogger(ClientCRMDaoImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryCRM")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<ClientNameAndEmail> getRegisteredClientsWithoutApplications(List<LocalDate> dateRegisteredList) {
        Session session = sessionFactory.openSession();
        List<ClientNameAndEmail> clientNameAndEmailList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select distinct on (cua.email)\n" +
                            "cua.email, cua.first_name\n" +
                            "from client_user_account cua \n" +
                            "left join loan_application la \n" +
                            "on cua.id = la.client_id \n" +
                            "where la.id is null\n" +
                            "and cua.email is not null\n" +
                            "and date(cua.created_at) in (:dateRegisteredList)")
                            .addScalar("email", new StringType())
                            .addScalar("first_name", new StringType())
                            .setParameter("dateRegisteredList", dateRegisteredList)
                            .list();
            for (Object[] row : rows) {
                ClientNameAndEmail clientNameAndEmail = new ClientNameAndEmail();
                if (row[0] != null) {
                    clientNameAndEmail.setEmail(row[0].toString());
                }
                if (row[1] != null) {
                    clientNameAndEmail.setName(row[1].toString());
                }
                if (clientNameAndEmail.getEmail() != null) {
                    clientNameAndEmailList.add(clientNameAndEmail);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return clientNameAndEmailList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ClientNameAndEmail> getRegisteredClientsWithoutApplications(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<ClientNameAndEmail> clientNameAndEmailList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select distinct on (cua.email)\n" +
                                    "cua.email, cua.first_name\n" +
                                    "from client_user_account cua \n" +
                                    "left join loan_application la \n" +
                                    "on cua.id = la.client_id \n" +
                                    "where la.id is null\n" +
                                    "and cua.email is not null\n" +
                                    "and date(cua.created_at) <= :dateFrom and date(cua.created_at) >= :dateTo")
                            .addScalar("email", new StringType())
                            .addScalar("first_name", new StringType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();
            for (Object[] row : rows) {
                ClientNameAndEmail clientNameAndEmail = new ClientNameAndEmail();
                if (row[0] != null) {
                    clientNameAndEmail.setEmail(row[0].toString());
                }
                if (row[1] != null) {
                    clientNameAndEmail.setName(row[1].toString());
                }
                if (clientNameAndEmail.getEmail() != null) {
                    clientNameAndEmailList.add(clientNameAndEmail);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return clientNameAndEmailList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ExportClientsWithExpiredForVicidial> getExpiredDebtsNotOwnNumbersForVicidial(List<Integer> overdueDays) {
        Session session = sessionFactory.openSession();
        List<ExportClientsWithExpiredForVicidial> clients = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select cp.phone, cua.first_name, cp.relationship_name,\n" +
                                    "dc.current_debt , dc.days_overdue , cua.document_id_number ,\n" +
                                    "bva.account , bva.bank_short_name \n" +
                                    "from debt_collection dc\n" +
                                    "left join client_phones cp \n" +
                                    "on cp.client_id = dc.client_id \n" +
                                    "left join client_user_account cua \n" +
                                    "on cua.id = dc.client_id \n" +
                                    "left join loan l \n" +
                                    "on l.id = dc.loan_id \n" +
                                    "left join baokim_virtual_account bva \n" +
                                    "on bva.application_id = l.application_id \n" +
                                    "where dc.finished is false\n" +
                                    "and cp.status = 'ACTUAL'\n" +
                                    "and dc.status <> 'Promised payment' \n" +
                                    "and cp.relationship_type <> 'OWN'\n" +
                                    "and dc.days_overdue in (:overdueDays)")
                            .addScalar("phone", new StringType())
                            .addScalar("first_name", new StringType())
                            .addScalar("relationship_name", new StringType())
                            .addScalar("current_debt", new DoubleType())
                            .addScalar("days_overdue", new IntegerType())
                            .addScalar("document_id_number", new StringType())
                            .addScalar("account", new StringType())
                            .addScalar("bank_short_name", new StringType())
                            .setParameter("overdueDays", overdueDays)
                            .list();
            for (Object[] row : rows) {
                ExportClientsWithExpiredForVicidial client = new ExportClientsWithExpiredForVicidial();
                if (row[0] != null) {
                    client.setPhone(row[0].toString());
                }
                if (row[1] != null) {
                    client.setFullName(row[1].toString());
                } else {
                    client.setFullName("");
                }
                if (row[2] != null) {
                    client.setRelationshipName(row[2].toString());
                } else {
                    client.setRelationshipName("");
                }
                if (row[3] != null) {
                    client.setCurrentDebt((int) BigDecimal.valueOf(Double.parseDouble(row[3].toString())).setScale(0, RoundingMode.UP).doubleValue());
                }
                if (row[4] != null) {
                    client.setDaysOverdue(Integer.parseInt(row[4].toString()));
                }
                if (row[5] != null) {
                    client.setDocumentNumber(row[5].toString());
                } else {
                    client.setDocumentNumber("");
                }
                if (row[6] != null) {
                    client.setBankAccount(row[6].toString());
                } else {
                    client.setBankAccount("");
                }
                if (row[7] != null) {
                    client.setBankShortName(row[7].toString());
                } else {
                    client.setBankShortName("");
                }
                if (client.getPhone() != null) {
                    client.setOwn(false);
                    clients.add(client);
                }
            }
        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + ": " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return clients;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ExportClientsWithExpiredForVicidial> getExpiredDebtsOwnNumbersForVicidial(List<Integer> overdueDays) {
        Session session = sessionFactory.openSession();
        List<ExportClientsWithExpiredForVicidial> clients = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select cp.phone, cua.first_name, cp.relationship_name,\n" +
                            "dc.current_debt , dc.days_overdue , cua.document_id_number ,\n" +
                            "bva.account , bva.bank_short_name \n" +
                            "from debt_collection dc\n" +
                            "left join client_phones cp \n" +
                            "on cp.client_id = dc.client_id \n" +
                            "left join client_user_account cua \n" +
                            "on cua.id = dc.client_id \n" +
                            "left join loan l \n" +
                            "on l.id = dc.loan_id \n" +
                            "left join baokim_virtual_account bva \n" +
                            "on bva.application_id = l.application_id \n" +
                            "where dc.finished is false\n" +
                            "and cp.status = 'ACTUAL'\n" +
                            "and dc.status <> 'Promised payment' \n" +
                            "and cp.relationship_type = 'OWN'\n" +
                            "and dc.days_overdue in (:overdueDays)")
                            .addScalar("phone", new StringType())
                            .addScalar("first_name", new StringType())
                            .addScalar("relationship_name", new StringType())
                            .addScalar("current_debt", new DoubleType())
                            .addScalar("days_overdue", new IntegerType())
                            .addScalar("document_id_number", new StringType())
                            .addScalar("account", new StringType())
                            .addScalar("bank_short_name", new StringType())
                            .setParameter("overdueDays", overdueDays)
                            .list();
            for (Object[] row : rows) {
                ExportClientsWithExpiredForVicidial client = new ExportClientsWithExpiredForVicidial();
                if (row[0] != null) {
                    client.setPhone(row[0].toString());
                }
                if (row[1] != null) {
                    client.setFullName(row[1].toString());
                } else {
                    client.setFullName("");
                }
                if (row[2] != null) {
                    client.setRelationshipName(row[2].toString());
                } else {
                    client.setRelationshipName("");
                }
                if (row[3] != null) {
                    client.setCurrentDebt((int) BigDecimal.valueOf(Double.parseDouble(row[3].toString())).setScale(0, RoundingMode.UP).doubleValue());
                }
                if (row[4] != null) {
                    client.setDaysOverdue(Integer.parseInt(row[4].toString()));
                }
                if (row[5] != null) {
                    client.setDocumentNumber(row[5].toString());
                } else {
                    client.setDocumentNumber("");
                }
                if (row[6] != null) {
                    client.setBankAccount(row[6].toString());
                } else {
                    client.setBankAccount("");
                }
                if (row[7] != null) {
                    client.setBankShortName(row[7].toString());
                } else {
                    client.setBankShortName("");
                }
                if (client.getPhone() != null) {
                    client.setOwn(true);
                    clients.add(client);
                }
            }
        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + ": " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return clients;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ExportPassiveClients> getPassiveClientsListForVicidial(List<LocalDate> dateLastFinishedLoanList) {
        Session session = sessionFactory.openSession();
        List<ExportPassiveClients> passiveClientsList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                            "cua.mobile_phone , cua.first_name,\n" +
                            "cast(EXTRACT(DAY FROM current_date - ff.max_finished_date) as INTEGER) as count_passive_days \n" +
                            "from client_user_account cua \n" +
                            "inner join\n" +
                            "(select l.client_id, max(l.finished_at) as max_finished_date from loan l\n" +
                            "where l.status = 'FINISHED'\n" +
                            "group by l.client_id ) as ff\n" +
                            "on ff.client_id = cua.id\n" +
                            "where not exists (select\n" +
                            "l2.client_id \n" +
                            "from loan l2 \n" +
                            "where l2.client_id = cua.id\n" +
                            "and l2.status <> 'FINISHED')\n" +
                            "and not exists (\n" +
                            "select cb.id from client_blacklist cb\n" +
                            "where cb.client_id = cua.id \n" +
                            "or cb.phone = cua.mobile_phone \n" +
                            "or cb.document_id_number = cua.document_id_number)\n" +
                            "and not exists (\n" +
                            "select la.id from loan_application la \n" +
                            "where la.status = 'REJECTED'\n" +
                            "and la.client_id = cua.id\n" +
                            "and date(la.requested_at) >= :maxDayQuarantin )\n" +
                            "and cua.email is not null\n" +
                            "and date(ff.max_finished_date) in (:dateLastFinishedLoanList)")
                            .addScalar("mobile_phone", new StringType())
                            .addScalar("first_name", new StringType())
                            .addScalar("count_passive_days", new IntegerType())
                            .setParameter("dateLastFinishedLoanList", dateLastFinishedLoanList)
                            .setParameter("maxDayQuarantin", LocalDate.now(ZoneId.of(TIME_ZONE)).minusDays(30))
                            .list();
            for (Object[] row : rows) {
                ExportPassiveClients passiveClients = new ExportPassiveClients();
                if (row[0] != null) {
                    passiveClients.setPhone(row[0].toString());
                }
                if (row[1] != null) {
                    passiveClients.setFullName(row[1].toString());
                }
                if (row[2] != null) {
                    passiveClients.setPassiveDays(Integer.parseInt(row[2].toString()));
                }
                if (passiveClients.getPhone() != null) {
                    passiveClientsList.add(passiveClients);
                }
            }
        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + ": " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return passiveClientsList;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<ClientNameAndEmail> getPassiveClientsWithoutLoans(List<LocalDate> dateLastFinishedLoanList) {
        Session session = sessionFactory.openSession();
        List<ClientNameAndEmail> clientNameAndEmailList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select distinct on (cua.email)\n" +
                                    "cua.email, cua.first_name\n" +
                                    "from client_user_account cua \n" +
                                    "inner join\n" +
                                    "(select l.client_id, max(l.finished_at) as max_finished_date from loan l\n" +
                                    "where l.status = 'FINISHED'\n" +
                                    "group by l.client_id ) as ff\n" +
                                    "on ff.client_id = cua.id\n" +
                                    "where not exists (select\n" +
                                    "l2.client_id \n" +
                                    "from loan l2 \n" +
                                    "where l2.client_id = cua.id\n" +
                                    "and l2.status <> 'FINISHED')\n" +
                                    "and cua.email <> 'khanhkeger11@gmail.com'\n" +
                                    "and cua.email is not null\n" +
                                    "and date(ff.max_finished_date) in (:dateLastFinishedLoanList)")
                            .addScalar("email", new StringType())
                            .addScalar("first_name", new StringType())
                            .setParameter("dateLastFinishedLoanList", dateLastFinishedLoanList)
                            .list();
            for (Object[] row : rows) {
                ClientNameAndEmail clientNameAndEmail = new ClientNameAndEmail();
                if (row[0] != null) {
                    clientNameAndEmail.setEmail(row[0].toString());
                }
                if (row[1] != null) {
                    clientNameAndEmail.setName(row[1].toString());
                }
                if (clientNameAndEmail.getEmail() != null) {
                    clientNameAndEmailList.add(clientNameAndEmail);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return clientNameAndEmailList;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<ClientNameAndEmail> getPassiveClientsWithoutLoans(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<ClientNameAndEmail> clientNameAndEmailList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select distinct on (cua.email)\n" +
                                    "cua.email, cua.first_name\n" +
                                    "from client_user_account cua \n" +
                                    "inner join\n" +
                                    "(select l.client_id, max(l.finished_at) as max_finished_date from loan l\n" +
                                    "where l.status = 'FINISHED'\n" +
                                    "group by l.client_id ) as ff\n" +
                                    "on ff.client_id = cua.id\n" +
                                    "where not exists (select\n" +
                                    "l2.client_id \n" +
                                    "from loan l2 \n" +
                                    "where l2.client_id = cua.id\n" +
                                    "and l2.status <> 'FINISHED')\n" +
                                    "and cua.email <> 'khanhkeger11@gmail.com'\n" +
                                    "and cua.email is not null\n" +
                                    "and date(ff.max_finished_date) <= :dateFrom and date(ff.max_finished_date) >= :dateTo ")
                            .addScalar("email", new StringType())
                            .addScalar("first_name", new StringType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();
            for (Object[] row : rows) {
                ClientNameAndEmail clientNameAndEmail = new ClientNameAndEmail();
                if (row[0] != null) {
                    clientNameAndEmail.setEmail(row[0].toString());
                }
                if (row[1] != null) {
                    clientNameAndEmail.setName(row[1].toString());
                }
                if (clientNameAndEmail.getEmail() != null) {
                    clientNameAndEmailList.add(clientNameAndEmail);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return clientNameAndEmailList;
    }



    @Override
    @SuppressWarnings("unchecked")
    public List<ClientNameAndEmail> getClientsWithExpiredLoansForNow(List<Integer> daysOverdueList) {
        Session session = sessionFactory.openSession();
        List<ClientNameAndEmail> clientNameAndEmailList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select distinct on (cua.email)\n" +
                                    "cua.email, cua.first_name\n" +
                                    "from debt_collection dc \n" +
                                    "left join client_user_account cua \n" +
                                    "on cua.id = dc.client_id \n" +
                                    "where dc.finished is false\n" +
                                    "and cua.email is not null\n" +
                                    "and dc.days_overdue in (:daysOverdueList)")
                            .addScalar("email", new StringType())
                            .addScalar("first_name", new StringType())
                            .setParameter("daysOverdueList", daysOverdueList)
                            .list();
            for (Object[] row : rows) {
                ClientNameAndEmail clientNameAndEmail = new ClientNameAndEmail();
                if (row[0] != null) {
                    clientNameAndEmail.setEmail(row[0].toString());
                }
                if (row[1] != null) {
                    clientNameAndEmail.setName(row[1].toString());
                }
                if (clientNameAndEmail.getEmail() != null) {
                    clientNameAndEmailList.add(clientNameAndEmail);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return clientNameAndEmailList;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<ClientNameAndEmail> getClientsWithExpiredLoansForNow(Integer daysFrom, Integer daysTo) {
        Session session = sessionFactory.openSession();
        List<ClientNameAndEmail> clientNameAndEmailList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select distinct on (cua.email)\n" +
                                    "cua.email, cua.first_name\n" +
                                    "from debt_collection dc \n" +
                                    "left join client_user_account cua \n" +
                                    "on cua.id = dc.client_id \n" +
                                    "where dc.finished is false\n" +
                                    "and cua.email is not null\n" +
                                    "and dc.days_overdue >= :daysFrom and dc.days_overdue <= :daysTo")
                            .addScalar("email", new StringType())
                            .addScalar("first_name", new StringType())
                            .setParameter("daysFrom", daysFrom)
                            .setParameter("daysTo", daysTo)
                            .list();
            for (Object[] row : rows) {
                ClientNameAndEmail clientNameAndEmail = new ClientNameAndEmail();
                if (row[0] != null) {
                    clientNameAndEmail.setEmail(row[0].toString());
                }
                if (row[1] != null) {
                    clientNameAndEmail.setName(row[1].toString());
                }
                if (clientNameAndEmail.getEmail() != null) {
                    clientNameAndEmailList.add(clientNameAndEmail);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return clientNameAndEmailList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ClientNameAndEmailRemindPayment> getClientsForRemindPayment(List<LocalDate> maturityDateList) {
        Session session = sessionFactory.openSession();
        List<ClientNameAndEmailRemindPayment> clientNameAndEmailRemindPaymentList = new ArrayList<>();
        DateTimeFormatter dfDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dfDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select distinct on (cua.email)\n" +
                            "cua.email,\n" +
                            "cua.first_name ,\n" +
                            "qq.period_end,\n" +
                            "l.contract_number ,\n" +
                            "l.contract_signed_at ,\n" +
                            "bva.account ,\n" +
                            "(ls.ch_collection_fee_overdue +\n" +
                            "ls.ch_consulting_fee_not_due +\n" +
                            "ls.ch_consulting_fee_overdue +\n" +
                            "ls.ch_gp_collection_fee_overdue +\n" +
                            "ls.ch_gp_penalty_interest_overdue +\n" +
                            "ls.ch_interest_not_due +\n" +
                            "ls.ch_interest_overdue +\n" +
                            "ls.ch_penalty_interest_overdue +\n" +
                            "ls.ch_principal +\n" +
                            "ls.ch_prolongation_fee) as debt,\n" +
                            "cua.document_id_number \n" +
                            "from\n" +
                            "(select li.loan_id, max(li.period_end) as period_end from loan_installment li\n" +
                            "where li.period_end in (:maturityDateList)\n" +
                            "group by li.loan_id) as qq\n" +
                            "left join loan l \n" +
                            "on l.id = qq.loan_id\n" +
                            "left join baokim_virtual_account bva \n" +
                            "on bva.application_id = l.application_id \n" +
                            "and bva.bank_short_name = 'VPBANK'\n" +
                            "left join loan_snapshot ls \n" +
                            "on ls.loan_id = qq.loan_id\n" +
                            "and ls.\"date\" = :dateNow\n" +
                            "left join client_user_account cua \n" +
                            "on cua.id = l.client_id \n" +
                            "where l.status not in ('FINISHED', 'EXPIRED')")
                            .addScalar("email", new StringType())
                            .addScalar("first_name", new StringType())
                            .addScalar("period_end", new LocalDateType())
                            .addScalar("contract_number", new StringType())
                            .addScalar("contract_signed_at", new LocalDateTimeType())
                            .addScalar("account", new StringType())
                            .addScalar("debt", new DoubleType())
                            .addScalar("document_id_number", new StringType())
                            .setParameter("maturityDateList", maturityDateList)
                            .setParameter("dateNow", LocalDate.now(ZoneId.of(TIME_ZONE)))
                            .list();
            for (Object[] row : rows) {
                ClientNameAndEmailRemindPayment clientNameAndEmailRemindPayment = new ClientNameAndEmailRemindPayment();
                if (row[0] != null) {
                    clientNameAndEmailRemindPayment.setEmail(row[0].toString());
                }
                if (row[1] != null) {
                    clientNameAndEmailRemindPayment.setName(row[1].toString());
                }
                if (row[2] != null) {
                    clientNameAndEmailRemindPayment.setMaturityDate(LocalDate.parse(row[2].toString(), dfDate));
                }
                if (row[3] != null) {
                    clientNameAndEmailRemindPayment.setContactNumber(row[3].toString());
                }
                if (row[4] != null) {
                    clientNameAndEmailRemindPayment.setContactSignedAt(LocalDateTime.parse(row[4].toString(), dfDateTime));
                }
                if (row[5] != null) {
                    clientNameAndEmailRemindPayment.setVirtualAccount(row[5].toString());
                }
                if (row[6] != null) {
                    clientNameAndEmailRemindPayment.setDebtAmount(BigDecimal.valueOf(Double.parseDouble(row[6].toString())).setScale(0, RoundingMode.UP).doubleValue());
                }
                if (row[7] != null) {
                    clientNameAndEmailRemindPayment.setDocumentNumber(row[7].toString());
                }
                if (clientNameAndEmailRemindPayment.getEmail() != null) {
                    clientNameAndEmailRemindPaymentList.add(clientNameAndEmailRemindPayment);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return clientNameAndEmailRemindPaymentList;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<ClientNameAndEmailExpiredLoan> getClientsWithExpiredLoansMoreInfo(List<Integer> overdueDays) {
        Session session = sessionFactory.openSession();
        List<ClientNameAndEmailExpiredLoan> clientNameAndEmailList = new ArrayList<>();
        DateTimeFormatter dfDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select distinct on (cua.email)\n" +
                                    "cua.email, cua.first_name,\n" +
                                    "cua.document_id_number ,\n" +
                                    "dc.current_debt as debt,\n" +
                                    "dc.days_overdue ,\n" +
                                    "l.contract_number ,\n" +
                                    "l.contract_signed_at ,\n" +
                                    "bva.account\n" +
                                    "from debt_collection dc \n" +
                                    "left join client_user_account cua \n" +
                                    "on cua.id = dc.client_id \n" +
                                    "left join loan l\n" +
                                    "on l.id = dc.loan_id \n" +
                                    "left join baokim_virtual_account bva \n" +
                                    "on bva.application_id = l.application_id \n" +
                                    "where dc.finished is false\n" +
                                    "and cua.email is not null\n" +
                                    "and dc.days_overdue in (:overdueDays)")
                            .addScalar("email", new StringType())
                            .addScalar("first_name", new StringType())
                            .addScalar("document_id_number", new StringType())
                            .addScalar("debt", new DoubleType())
                            .addScalar("days_overdue", new IntegerType())
                            .addScalar("contract_number", new StringType())
                            .addScalar("contract_signed_at", new LocalDateTimeType())
                            .addScalar("account", new StringType())
                            .setParameter("overdueDays", overdueDays)
                            .list();
            for (Object[] row : rows) {
                ClientNameAndEmailExpiredLoan clientNameAndEmail = new ClientNameAndEmailExpiredLoan();
                if (row[0] != null) {
                    clientNameAndEmail.setEmail(row[0].toString());
                }
                if (row[1] != null) {
                    clientNameAndEmail.setName(row[1].toString());
                }
                if (row[2] != null) {
                    clientNameAndEmail.setDocumentNumber(row[2].toString());
                }
                if (row[3] != null) {
                    clientNameAndEmail.setDebtAmount(BigDecimal.valueOf(Double.parseDouble(row[3].toString())).setScale(0, RoundingMode.UP).doubleValue());
                }
                if (row[4] != null) {
                    clientNameAndEmail.setDaysOverdue(Integer.parseInt(row[4].toString()));
                }
                if (row[5] != null) {
                    clientNameAndEmail.setContactNumber(row[5].toString());
                }
                if (row[6] != null) {
                    clientNameAndEmail.setContractSignedAt(LocalDateTime.parse(row[6].toString(), dfDateTime));
                }
                if (row[7] != null) {
                    clientNameAndEmail.setVirtualAccount(row[7].toString());
                }
                if (clientNameAndEmail.getEmail() != null) {
                    clientNameAndEmailList.add(clientNameAndEmail);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return clientNameAndEmailList;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<ClientNameAndEmailGracePeriod> getClientsForRemindPaymentGracePeriod() {
        Session session = sessionFactory.openSession();
        List<ClientNameAndEmailGracePeriod> clientNameAndEmailList = new ArrayList<>();
        DateTimeFormatter dfDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select distinct on (cua.email)\n" +
                                    "cua.email,\n" +
                                    "cua.first_name ,\n" +
                                    "l.contract_number ,\n" +
                                    "l.contract_signed_at ,\n" +
                                    "bva.account ,\n" +
                                    "(ls.ch_collection_fee_overdue +\n" +
                                    "ls.ch_consulting_fee_not_due +\n" +
                                    "ls.ch_consulting_fee_overdue +\n" +
                                    "ls.ch_gp_collection_fee_overdue +\n" +
                                    "ls.ch_gp_penalty_interest_overdue +\n" +
                                    "ls.ch_interest_not_due +\n" +
                                    "ls.ch_interest_overdue +\n" +
                                    "ls.ch_penalty_interest_overdue +\n" +
                                    "ls.ch_principal +\n" +
                                    "ls.ch_prolongation_fee) as debt,\n" +
                                    "cua.document_id_number \n" +
                                    "from\n" +
                                    "loan l \n" +
                                    "left join baokim_virtual_account bva \n" +
                                    "on bva.application_id = l.application_id \n" +
                                    "and bva.bank_short_name = 'VPBANK'\n" +
                                    "left join loan_snapshot ls \n" +
                                    "on ls.loan_id = l.id\n" +
                                    "and ls.\"date\" = :dateNow\n" +
                                    "left join client_user_account cua \n" +
                                    "on cua.id = l.client_id \n" +
                                    "where l.status = 'GRACE'")
                            .addScalar("email", new StringType())
                            .addScalar("first_name", new StringType())
                            .addScalar("contract_number", new StringType())
                            .addScalar("contract_signed_at", new LocalDateTimeType())
                            .addScalar("account", new StringType())
                            .addScalar("debt", new DoubleType())
                            .addScalar("document_id_number", new StringType())
                            .setParameter("dateNow", LocalDate.now(ZoneId.of(TIME_ZONE)))
                            .list();
            for (Object[] row : rows) {
                ClientNameAndEmailGracePeriod clientNameAndEmail = new ClientNameAndEmailGracePeriod();
                if (row[0] != null) {
                    clientNameAndEmail.setEmail(row[0].toString());
                }
                if (row[1] != null) {
                    clientNameAndEmail.setName(row[1].toString());
                }
                if (row[2] != null) {
                    clientNameAndEmail.setContactNumber(row[2].toString());
                }
                if (row[3] != null) {
                    clientNameAndEmail.setContactSignedAt(LocalDateTime.parse(row[3].toString(), dfDateTime));
                }
                if (row[4] != null) {
                    clientNameAndEmail.setVirtualAccount(row[4].toString());
                }
                if (row[5] != null) {
                    clientNameAndEmail.setDebtAmount(BigDecimal.valueOf(Double.parseDouble(row[5].toString())).setScale(0, RoundingMode.UP).doubleValue());
                }
                if (row[6] != null) {
                    clientNameAndEmail.setDocumentNumber(row[6].toString());
                }
                if (clientNameAndEmail.getEmail() != null) {
                    clientNameAndEmailList.add(clientNameAndEmail);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return clientNameAndEmailList;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<ClientNameAndEmail> getClientsWithSomeLastApplications(List<LocalDate> dateAppChangedStatusList,
                                                                       String applicationStatus) {
        Session session = sessionFactory.openSession();
        List<ClientNameAndEmail> clientNameAndEmailList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select distinct on (cua.email)\n" +
                                    "cua.email, cua.first_name\n" +
                                    "from client_user_account cua\n" +
                                    "inner join\n" +
                                    "(select\n" +
                                    "la.client_id,\n" +
                                    "max(la.id) as last_app_id\n" +
                                    "from loan_application la\n" +
                                    "group by la.client_id) as qq\n" +
                                    "on qq.client_id = cua.id\n" +
                                    "inner join loan_application la2 \n" +
                                    "on la2.id = qq.last_app_id\n" +
                                    "where date(la2.status_changed_at) in (:dateAppChangedStatusList)\n" +
                                    "and la2.status = :applicationStatus")
                            .addScalar("email", new StringType())
                            .addScalar("first_name", new StringType())
                            .setParameter("dateAppChangedStatusList", dateAppChangedStatusList)
                            .setParameter("applicationStatus", applicationStatus)
                            .list();
            for (Object[] row : rows) {
                ClientNameAndEmail clientNameAndEmail = new ClientNameAndEmail();
                if (row[0] != null) {
                    clientNameAndEmail.setEmail(row[0].toString());
                }
                if (row[1] != null) {
                    clientNameAndEmail.setName(row[1].toString());
                }
                if (clientNameAndEmail.getEmail() != null) {
                    clientNameAndEmailList.add(clientNameAndEmail);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return clientNameAndEmailList;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<ClientNameAndEmail> getClientsWithBirthDate(LocalDate birthDate) {
        int day = birthDate.getDayOfMonth();
        int month = birthDate.getMonthValue();
        Session session = sessionFactory.openSession();
        List<ClientNameAndEmail> clientNameAndEmailList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select distinct on (cua.email)\n" +
                                    "cua.email, cua.first_name\n" +
                                    "from client_user_account cua\n" +
                                    "where extract(month from cua.birth_date) = :month\n" +
                                    "and extract(day from cua.birth_date) = :day")
                            .addScalar("email", new StringType())
                            .addScalar("first_name", new StringType())
                            .setParameter("month", month)
                            .setParameter("day", day)
                            .list();
            for (Object[] row : rows) {
                ClientNameAndEmail clientNameAndEmail = new ClientNameAndEmail();
                if (row[0] != null) {
                    clientNameAndEmail.setEmail(row[0].toString());
                }
                if (row[1] != null) {
                    clientNameAndEmail.setName(row[1].toString());
                }
                if (clientNameAndEmail.getEmail() != null) {
                    clientNameAndEmailList.add(clientNameAndEmail);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return clientNameAndEmailList;
    }



    @Override
    @SuppressWarnings("unchecked")
    public List<ClientEmailConfirmedCRM> getClientEmailConfirmedCRMList(List<LocalDate> dateRegisteredList) {
        Session session = sessionFactory.openSession();
        List<ClientEmailConfirmedCRM> clientEmailConfirmedCRMList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select cua.id, cua.email, cua.email_confirmed, cua.first_name from client_user_account cua \n" +
                            "where date(cua.created_at) in (:dateRegisteredList) \n" +
                            "and cua.email_confirmed is false \n" +
                            "and cua.email is not null")
                            .addScalar("id", new LongType())
                            .addScalar("email", new StringType())
                            .addScalar("email_confirmed", new BooleanType())
                            .addScalar("first_name", new StringType())
                            .setParameter("dateRegisteredList", dateRegisteredList)
                            .list();
            for (Object[] row : rows) {
                ClientEmailConfirmedCRM clientEmailConfirmedCRM = new ClientEmailConfirmedCRM();
                if (row[0] != null) {
                    clientEmailConfirmedCRM.setClientId(Long.parseLong(row[0].toString()));
                }
                if (row[1] != null) {
                    clientEmailConfirmedCRM.setEmail(row[1].toString());
                }
                if (row[2] != null) {
                    clientEmailConfirmedCRM.setEmailConfirmed(Boolean.getBoolean(row[2].toString()));
                }
                if (row[3] != null) {
                    clientEmailConfirmedCRM.setName(row[3].toString());
                }
                if (clientEmailConfirmedCRM.getEmail() != null) {
                    clientEmailConfirmedCRMList.add(clientEmailConfirmedCRM);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return clientEmailConfirmedCRMList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ClientEmailConfirmedCRM getClientEmailConfirmedById(Long clientId) {
        Session session = sessionFactory.openSession();
        ClientEmailConfirmedCRM clientEmailConfirmedCRM = new ClientEmailConfirmedCRM();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                            "cua.id,\n" +
                            "cua.email,\n" +
                            "cua.email_confirmed,\n" +
                            "cua.first_name \n" +
                            "from client_user_account cua\n" +
                            "where cua.id = :clientId\n" +
                            "limit 1")
                            .addScalar("id", new LongType())
                            .addScalar("email", new StringType())
                            .addScalar("email_confirmed", new BooleanType())
                            .addScalar("first_name", new StringType())
                            .setParameter("clientId", clientId)
                            .list();
            if (rows.size() > 0) {
                if (rows.get(0)[0] != null) {
                    clientEmailConfirmedCRM.setClientId(Long.parseLong(rows.get(0)[0].toString()));
                }
                if (rows.get(0)[1] != null) {
                    clientEmailConfirmedCRM.setEmail(rows.get(0)[1].toString());
                }
                if (rows.get(0)[2] != null) {
                    clientEmailConfirmedCRM.setEmailConfirmed(Boolean.getBoolean(rows.get(0)[2].toString()));
                }
                if (rows.get(0)[3] != null) {
                    clientEmailConfirmedCRM.setName(rows.get(0)[3].toString());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return clientEmailConfirmedCRM;
    }


    @Override
    public void setEmailConfirmedToTrue(Long clientId) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        try {
            session.createSQLQuery("update client_user_account\n" +
                            "set email_confirmed = true\n" +
                            "where id = :clientId")
                    .setParameter("clientId", clientId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void createClientPhone(ClientPhoneInsertCRM phoneInsertCRM) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        try {
            session.createSQLQuery("insert into client_phones (created_at, last_modified_at, created_by_info, client_id, comment, phone, relationship_name, relationship_type,\n" +
                    "                           source, status) values (:createdAt, :lastModifiedAt, :createdByInfo, :clientId, :comment, :phone, :relationshipName, :relationshipType, :source, :status)")
                    .setParameter("createdAt", phoneInsertCRM.getCreatedAt())
                    .setParameter("lastModifiedAt", phoneInsertCRM.getLastModifiedAt())
                    .setParameter("clientId", phoneInsertCRM.getClientId())
                    .setParameter("comment", phoneInsertCRM.getComment())
                    .setParameter("phone", phoneInsertCRM.getPhone())
                    .setParameter("relationshipName", phoneInsertCRM.getRelationshipName())
                    .setParameter("relationshipType", phoneInsertCRM.getRelationshipType())
                    .setParameter("createdByInfo", phoneInsertCRM.getSource())
                    .setParameter("source", "BACK_USER")
                    .setParameter("status", "ACTUAL")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
    }


    @Override
    @Modifying
    public void setEmailConfirmedToTrueAndAddAttempt(Long clientId) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        try {
            session.createSQLQuery("update client_user_account \n" +
                            "set email_confirmed = true,\n" +
                            "free_attempts_wheel_fortune = free_attempts_wheel_fortune + 1 \n" +
                            "where id = :clientId")
                    .setParameter("clientId", clientId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getClientContactsByClientId(Long clientId) {
        Session session = sessionFactory.openSession();
        List<String> clientContactList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select cc.contact from client_contacts cc \n" +
                            "where cc.client_id = :clientId")
                            .addScalar("contact", new StringType())
                            .setParameter("clientId", clientId)
                            .list();
            for (Object[] row : rows) {
                if (row[0] != null) {
                    clientContactList.add(row[0].toString());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return clientContactList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getClientPhonesByClientId(Long clientId) {
        Session session = sessionFactory.openSession();
        List<String> clientPhoneList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select cp.phone from client_phones cp\n" +
                            "where cp.client_id = :clientId")
                            .addScalar("phone", new StringType())
                            .setParameter("clientId", clientId)
                            .list();
            for (Object[] row : rows) {
                if (row[0] != null) {
                    clientPhoneList.add(row[0].toString());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return clientPhoneList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean existClientContact(String contact, Long clientId) {
        Session session = sessionFactory.openSession();
        boolean result;
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select cc.id from client_contacts cc\n" +
                                    "where cc.client_id = :clientId and cc.contact = :contact")
                            .addScalar("id", new LongType())
                            .setParameter("clientId", clientId)
                            .setParameter("contact", contact)
                            .list();
            result = !rows.isEmpty();
        } catch (Exception e) {
            logger.error(e.getMessage());
            result = true;
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public void createSendingForCheckingInsurance(List<String> documentNumberList, String createdByEmail) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        try {
            session.createSQLQuery("insert into sending_for_checking_insurance(client_id, created_at, created_by_email)\n" +
                    "select cua.id, :createdAt, :createdByEmail from client_user_account cua \n" +
                    "where cua.document_id_number in (:documentNumberList)")
                    .setParameter("createdAt", LocalDateTime.now(ZoneId.of(TIME_ZONE)))
                    .setParameter("createdByEmail", createdByEmail)
                    .setParameter("documentNumberList", documentNumberList)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void createClientAddress(ClientAddressInsertCRM addressInsertCRM) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        try {
            session.createSQLQuery("insert into client_addresses (created_at, last_modified_at, created_by_info, city, client_id, comment, district, house_apartment_room_number, province, relationship_name, relationship_type,\n" +
                            "source, status, street) values (:createdAt, :lastModifiedAt, :createdByInfo, :city, :clientId, :comment, :district, :house_apartment_room_number, :province, :relationshipName, :relationshipType, :source, :status, :street)")
                    .setParameter("createdAt", addressInsertCRM.getCreatedAt())
                    .setParameter("lastModifiedAt", addressInsertCRM.getLastModifiedAt())
                    .setParameter("city", "-")
                    .setParameter("clientId", addressInsertCRM.getClientId())
                    .setParameter("comment", addressInsertCRM.getComment())
                    .setParameter("district", "-")
                    .setParameter("house_apartment_room_number", "-")
                    .setParameter("province", "-")
                    .setParameter("relationshipName", addressInsertCRM.getRelationshipName())
                    .setParameter("relationshipType", addressInsertCRM.getRelationshipType())
                    .setParameter("createdByInfo", addressInsertCRM.getSource())
                    .setParameter("source", "BACK_USER")
                    .setParameter("status", "ACTUAL")
                    .setParameter("street", addressInsertCRM.getAddress())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean existClientAddressInStreet(String fullAddress, Long clientId) {
        Session session = sessionFactory.openSession();
        boolean result;
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select cc.id from client_addresses cc\n" +
                                    "where cc.client_id = :clientId and cc.street = :street")
                            .addScalar("id", new LongType())
                            .setParameter("clientId", clientId)
                            .setParameter("street", fullAddress)
                            .list();
            result = !rows.isEmpty();
        } catch (Exception e) {
            logger.error(e.getMessage());
            result = true;
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean existClientPhone(String phone, Long clientId) {
        Session session = sessionFactory.openSession();
        boolean result;
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select cp.id from client_phones cp\n" +
                            "where cp.client_id = :clientId and cp.phone = :phone")
                            .addScalar("id", new LongType())
                            .setParameter("clientId", clientId)
                            .setParameter("phone", phone)
                            .list();
            result = !rows.isEmpty();
        } catch (Exception e) {
            logger.error(e.getMessage());
            result = true;
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Long> getClientIdsByDocumentNumber(String documentNumber) {
        Session session = sessionFactory.openSession();
        List<Long> clientIdList = new ArrayList<>();
        try {
            List<Object> rows =
                    session.createSQLQuery("select cua.id as client_id from client_user_account cua \n" +
                            "where cua.document_id_number = :documentNumber")
                            .addScalar("client_id", new LongType())
                            .setParameter("documentNumber", documentNumber)
                            .list();
            for (Object row : rows) {
                if (row != null) {
                    clientIdList.add(Long.parseLong(row.toString()));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return clientIdList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ClientDocumentAndName> getClientDocumentForCheckInsurance() {
        Session session = sessionFactory.openSession();
        List<ClientDocumentAndName> documentAndNameList = new ArrayList<>();
        DateTimeFormatter dfDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                                    "q.document_id_number,\n" +
                                    "q.first_name,\n" +
                                    "q.birth_date\n" +
                                    "from\n" +
                                    "(select distinct on(dc.client_id)\n" +
                                    "cua.document_id_number ,\n" +
                                    "cua.first_name ,\n" +
                                    "cua.birth_date ,\n" +
                                    "cua.id\n" +
                                    "from debt_collection dc \n" +
                                    "left join client_user_account cua \n" +
                                    "on cua.id = dc.client_id \n" +
                                    "where dc.days_overdue between 3 and 200\n" +
                                    "and dc.finished is false) as q\n" +
                                    "where not exists (\n" +
                                    "select sfci.id from sending_for_checking_insurance sfci\n" +
                                    "where sfci.client_id = q.id)")
                            .addScalar("document_id_number", new StringType())
                            .addScalar("first_name", new StringType())
                            .addScalar("birth_date", new LocalDateType())
                            .list();
            for (Object[] row : rows) {
                ClientDocumentAndName clientDocumentAndName = new ClientDocumentAndName();
                if (row[0] != null) {
                    clientDocumentAndName.setDocumentNumber(row[0].toString());
                }
                if (row[1] != null) {
                    clientDocumentAndName.setFullName(row[1].toString());
                }
                if (row[2] != null) {
                    clientDocumentAndName.setBirthDate(LocalDate.parse(row[2].toString(), dfDate));
                }
                if (clientDocumentAndName.getDocumentNumber() != null) {
                    documentAndNameList.add(clientDocumentAndName);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return documentAndNameList;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<ClientNameAndEmail> getClientsWithActiveLoans() {
        Session session = sessionFactory.openSession();
        List<ClientNameAndEmail> clientNameAndEmailList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select distinct on (cua.email)\n" +
                                    "cua.email, cua.first_name\n" +
                                    "from client_user_account cua\n" +
                                    "inner join\n" +
                                    "loan l\n" +
                                    "on l.client_id = cua.id\n" +
                                    "where l.status not in ('EXPIRED', 'FINISHED')")
                            .addScalar("email", new StringType())
                            .addScalar("first_name", new StringType())
                            .list();
            for (Object[] row : rows) {
                ClientNameAndEmail clientNameAndEmail = new ClientNameAndEmail();
                if (row[0] != null) {
                    clientNameAndEmail.setEmail(row[0].toString());
                }
                if (row[1] != null) {
                    clientNameAndEmail.setName(row[1].toString());
                }
                if (clientNameAndEmail.getEmail() != null) {
                    clientNameAndEmailList.add(clientNameAndEmail);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return clientNameAndEmailList;
    }

}
