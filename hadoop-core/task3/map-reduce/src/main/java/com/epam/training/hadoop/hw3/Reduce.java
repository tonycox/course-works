package com.epam.training.hadoop.hw3;

import com.epam.training.hadoop.hw3.utils.BytesInfo;
import com.epam.training.hadoop.hw3.utils.CommonSumReducer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Anton_Solovev
 * @since 8/12/2016.
 */
public class Reduce extends Reducer<Text, BytesInfo, Text, Text> {
    private LongAdder counter = new LongAdder();
    private CommonSumReducer reducer = new CommonSumReducer();

    @Override
    protected void reduce(Text key, Iterable<BytesInfo> values, Context context)
            throws IOException, InterruptedException {
        Optional<Long> sumOfBytesOpt = reducer.reduceToSumOfBytes(values, counter);
        Long sum = sumOfBytesOpt.orElse(0L);
        Long avg = (sum / counter.sumThenReset());
        String str = String.format(",%d,%d", avg, sum);
        context.write(key, new Text(str));
    }


}
