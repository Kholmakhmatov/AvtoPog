package uz.agrobank.avtopog.baseUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.agrobank.avtopog.dto.UserDto;
import uz.agrobank.avtopog.mapper.MyMapper;
import uz.agrobank.avtopog.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class MyBaseUtil {
    private final MyMapper myMapper;

    public UserDto userDto() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return myMapper.fromUser(principal);
    }
    public TreeSet<Integer> generateCount(Integer count, Integer page) {
        TreeSet<Integer> integerList = new TreeSet<>();
        int counter = 4;
        if (count < 15) {
            for (Integer i = 1; i <= count; i++) {
                integerList.add(i);
            }
        } else {
            for (int i = 1; i < 4; i++) {
                integerList.add(i);
            }
            for (int i = page; i > 0 && counter > 0; i--) {
                integerList.add(i);
                counter--;
            }
            for (int i = page; i < count && counter < 5; i++) {
                integerList.add(i);
                counter++;
            }
            for (Integer i = count - 4; i <= count; i++) {
                integerList.add(i);
            }
            integerList.add(page);
            integerList.add((int) (count * 0.20));
            integerList.add((int) (count * 0.40));
            integerList.add((int) (count * 0.60));
            integerList.add((int) (count * 0.80));
        }
        return integerList;
    }

    public List<Integer> getUntilYears(int i) {
        List<Integer>list=new ArrayList<>();
        int year = LocalDate.now().getYear();
        for (int j = 0; j <= i; j++) {
            list.add(year+j);
        }
        return list;
    }
    public List<Integer> getOtDo(int start,int end) {
        List<Integer>list=new ArrayList<>();
        for (int j = start; j <= end; j++) {
            list.add(j);
        }
        return list;
    }
}
