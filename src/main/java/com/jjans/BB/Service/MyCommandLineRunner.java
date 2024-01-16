package com.jjans.BB.Service;
import com.jjans.BB.Entity.UsersDocument;
import com.jjans.BB.Repository.UsersSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    private final UsersSearchRepository usersDocumentRepository;

    @Autowired
    public MyCommandLineRunner(UsersSearchRepository usersDocumentRepository) {
        this.usersDocumentRepository = usersDocumentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        checkDataInElasticsearch();
    }

    private void checkDataInElasticsearch() {
        Iterable<UsersDocument> allUsersDocuments = usersDocumentRepository.findAll();
        // 출력 혹은 로깅을 통해 데이터 확인
        allUsersDocuments.forEach(System.out::println);
    }
}
