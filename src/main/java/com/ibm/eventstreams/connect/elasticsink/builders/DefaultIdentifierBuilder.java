/**
 * Copyright 2020 IBM Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.ibm.eventstreams.connect.elasticsink.builders;

import com.ibm.eventstreams.connect.elasticsink.ElasticWriter;
import org.apache.kafka.connect.sink.SinkRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Builds document identifiers from Kafka Connect SinkRecords
 */
public class DefaultIdentifierBuilder implements IdentifierBuilder {
    private static final Logger log = LoggerFactory.getLogger(ElasticWriter.class);
    /**
     * Convert a Kafka Connect SinkRecord into a document identifier.
     *
     * @param record             the Kafka Connect SinkRecord
     *
     * @return the document identifier
     */


    @Override
    public String fromSinkRecord(SinkRecord record)
    {
        log.info("Using Default Id Builder");
        // - A unique id is made from the Kafka topic, partition and offset. That allows us
        //   to reinsert the same document after a failure/retry cycle. While the
        //   new document will get an updated version tag if it's already been inserted,
        //   the content will be preserved.
        return record.topic() + "!" + record.kafkaPartition() + "!" + record.kafkaOffset();
    }

    /**
     * Whether document identifiers generated by this builder are unique.
     * 
     * @return true if unique, false if not
     */
    @Override
    public boolean isUnique() {
        return true;
    }
}