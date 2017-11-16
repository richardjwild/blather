package com.github.richardjwild.blather.time;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Instant;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimestampFormatterShould {

    @Mock
    private Clock clock;

    @Test
    public void format_a_timestamp_from_just_now_as_0_seconds_ago() {
        Instant timestamp = Instant.now();
        when(clock.now()).thenReturn(timestamp);
        TimestampFormatter timestampFormatter = new TimestampFormatter(clock);

        String formattedTimestamp = timestampFormatter.format(timestamp);

        assertThat(formattedTimestamp).isEqualTo("(0 seconds ago)");
    }

}