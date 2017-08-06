package com.concurrency.repository;

import com.concurrency.model.Lender;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LenderRepositoryTest {

    @InjectMocks
    LenderRepository lenderRepository;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllLender() throws IOException{
        List<Lender> lenders = lenderRepository.findAllLendersSortedByRate("lender_data.csv");
        assertEquals(7, lenders.size());
    }
}