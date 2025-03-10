package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeMutationQuery(
                    "CREATE TABLE IF NOT EXISTS User (" +
                            "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                            "name VARCHAR(50), " +
                            "lastName VARCHAR(50), " +
                            "age TINYINT)").executeUpdate();
            transaction.commit();
            System.out.println("Таблица User создана!");
        } catch (Exception e) {
            rollbackIfNeeded(transaction);
            System.out.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeMutationQuery("DROP TABLE IF EXISTS User").executeUpdate();
            transaction.commit();
            System.out.println("Таблица User удалена!");
        } catch (Exception e) {
            rollbackIfNeeded(transaction);
            System.out.println("Ошибка при удалении таблицы: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.persist(user);
            transaction.commit();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (Exception e) {
            rollbackIfNeeded(transaction);
            System.out.println("Ошибка при добавлении пользователя: " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.find(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            transaction.commit();
            System.out.println("Пользователь с id=" + id + " удалён");
        } catch (Exception e) {
            rollbackIfNeeded(transaction);
            System.out.println("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            List<User> users = session.createQuery("FROM User", User.class).getResultList();
            users.forEach(System.out::println);
            System.out.println("Получен список всех пользователей");
            return users;
        } catch (Exception e) {
            System.out.println("Ошибка при получении пользователей: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeMutationQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
            System.out.println("Все данные из таблицы user удалены!");
        } catch (Exception e) {
            rollbackIfNeeded(transaction);
            System.out.println("Ошибка при очистке таблицы: " + e.getMessage());
        }
    }

    private void rollbackIfNeeded(Transaction transaction) {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        System.err.println("Произошла ошибка, откат транзакции");
    }
}
