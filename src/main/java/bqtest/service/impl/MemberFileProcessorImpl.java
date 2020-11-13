package bqtest.service.impl;

import bqtest.service.Member;
import bqtest.service.MemberFileProcessor;
import bqtest.service.MemberImporter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MemberFileProcessorImpl extends MemberFileProcessor {

    /*
     * Implement methods here
     */

    @Override
    protected MemberImporter getMemberImporter() {
          return new MemberImporterImpl();
    }

    @Override
    protected List<Member> getNonDuplicateMembers(List<Member> membersFromFile) {
        Map<String, Member> membersMap = new HashMap<String, Member>();
        for(Member member: membersFromFile ) {
            membersMap.put(member.getId(), member );

        }
        membersFromFile.clear();

        for (Map.Entry<String, Member> memberEntrySet : membersMap.entrySet()) {
            membersFromFile.add(memberEntrySet.getValue());
        }
        membersMap.clear();

        return membersFromFile;
    }

    @Override
    protected Map<String, List<Member>> splitMembersByState(List<Member> validMembers) {
        Map <String, List < Member >> mapWithState = new LinkedHashMap<String, List< Member>>();
        Set<String> statesSet = new HashSet<>();
        validMembers.stream().forEach(memb -> {
            statesSet.add(memb.getState());
        });


        statesSet.stream().forEach(state -> {
            List <Member> memberPerState = validMembers.stream().filter(memb -> memb.getState().equals(state)).collect(Collectors.toList());
            mapWithState.put(state, memberPerState);
        });

        return mapWithState;
    }

}
