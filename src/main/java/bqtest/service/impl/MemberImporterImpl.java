package bqtest.service.impl;

import bqtest.service.Member;
import bqtest.service.MemberImporter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MemberImporterImpl implements MemberImporter {

    public List<Member> importMembers(File inputFile) throws IOException {
        return Files.lines(inputFile.toPath())
                .map(line -> {
                    Member member = new Member();
                    String[] chunks = line.split(("\\s+"));

                    member.setId(chunks[0]);
                    member.setLastName(chunks[1]);
                    member.setFirstName(chunks[2]);
                    member.setAddress(chunks[3] + " " + chunks[4] + " " + chunks[5]);

                    StringBuffer city = new StringBuffer();
                    StringBuffer state = new StringBuffer();
                    city.append(chunks[6]);
                    state.append( chunks[chunks.length - 2]);
                    if (chunks.length > 9) {
                        city.append( " " + chunks[7]);

                    }
                    if(chunks.length > 10) {
                        city.append(  " " + chunks[8]);
                    }

                    int stateLength = (chunks[chunks.length - 2].length());
                    if (stateLength > 2) {
                        state.delete( 0, stateLength - 2);
                        city.append( " " + chunks[8]);
                        city.delete(city.length() - 2, city.length());

                    }
                    member.setCity(city.toString());
                    member.setState(state.toString());

                    member.setZip(chunks[chunks.length - 1]);

                    return member;
                   //TODO implement here
                    //return new Member();
                }).collect(Collectors.toList());
    }

}
