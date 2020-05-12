package com.information.center.accountservice.repository;

import lombok.var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountRepositoryImplTest {

    @InjectMocks
    private AccountRepositoryImpl accountRepository;

    @Mock
    private EntityManager entityManager;

    @Test
    public void findAllByYearForDays_expectMapForDays() {

        TypedQuery query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), (Class<Tuple>) any())).thenReturn(query);
        List<Tuple> test = new ArrayList<>();
        Mockito.when(query.getResultList()).thenReturn(test);
        var result = accountRepository.findAllByYearForDays("2019");
        assertNotNull(result);
    }

    @Test
    public void findAllByYearForWeeks_expectMapForWeeks() {
        TypedQuery query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), (Class<Tuple>) any())).thenReturn(query);
        List<Tuple> test = new ArrayList<>();
        Mockito.when(query.getResultList()).thenReturn(test);
        var result = accountRepository.findAllByYearForWeeks("2019");
        assertNotNull(result);
    }
}
