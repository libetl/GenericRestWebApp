package org.toilelibre.libe.restwebapp.ioc;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.springframework.context.annotation.Bean;

public class LuceneSpellCheckerConfig {

    @Bean
    public IndexWriterConfig getIndexWriterConfig () {
        return new IndexWriterConfig (Version.LUCENE_36, new StandardAnalyzer (Version.LUCENE_36));
    }

    @Bean
    public SpellChecker getSpellChecker () throws IOException {
        return new SpellChecker (new RAMDirectory ());
    }
}
