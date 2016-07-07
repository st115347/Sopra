package de.uni.hohenheim.sopra.projekt.config;

import de.uni.hohenheim.sopra.projekt.UserService;
import de.uni.hohenheim.sopra.projekt.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by bumiller on 06.06.2016.
 */
@Transactional
@Component
public class TestSetup implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  private UserDetailsManager userDetailsManager;
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;
  @Autowired
  private SopraUserRepository userRepository;

  @Autowired
  private LearningGroupRepository learningGroupRepository;

  @Autowired
  private SopraUserRepository sopraUserRepository;

  @Autowired
  private MCquestionRepository mCquestionRepository;



  /**
   * Handle an application event.
   *
   * @param event the event to respond to
   */
  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    Collection<GrantedAuthority> authsAdmin = new ArrayList<>();

    SopraUser user = new SopraUser();
    authsAdmin.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    userDetailsManager.createUser(new User("a", passwordEncoder.encode("b"), authsAdmin));
    user.setUsername("a");
    user.setPasswort("b");
    user.setEmail("a");
    user.setVorname("Tabea");
    user.setNachname("Schmid");
    sopraUserRepository.save(user);

    SopraUser user1 = new SopraUser();
    authsAdmin.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    userDetailsManager.createUser(new User("asdf", passwordEncoder.encode("asdf"), authsAdmin));
    user1.setUsername("asdf");
    user1.setPasswort("asdf");
    user1.setEmail("asdf");
    user1.setNachname("Peter");
    user1.setVorname("Hans");
    sopraUserRepository.save(user1);
    LearningGroup l = new LearningGroup();
    l.setId(1);
    l.setName("Modellierung");
    l.setVisibility(true);
    LearningGroup l1 = new LearningGroup();
    l1.setId(2);
    l1.setName("Einführung in die Softwaretechnik");
    l1.setVisibility(true);
    LearningGroup l2 = new LearningGroup();
    l2.setId(3);
    l2.setName("Sopra");
    l2.setPassword("asdf");
    l2.setVisibility(false);
    LearningGroup l3 = new LearningGroup();
    l3.setId(4);
    l3.setName("Mbis");
    l3.setVisibility(true);
    l.addUser(user1);
    l.addUser(user);
    l1.addUser(user1);
    l2.addUser(user1);
    l3.addUser(user1);

    learningGroupRepository.save(l);
    learningGroupRepository.save(l1);
    learningGroupRepository.save(l2);
    learningGroupRepository.save(l3);

    MCquestion question1= new MCquestion();
    question1.setThema("Harry Potter");
    question1.setAuthor("Hans Peter");
    question1.setAuthorID(user1.getUsername());
    question1.setFrage("Wer hat Dumbledore getötet?");
    question1.setId(1);
    question1.setAntwort1("Harry Potter");
    question1.setAntwort1_lsg(2);
    question1.setAntwort2("Severus Snape");
    question1.setAntwort2_lsg(1);
    question1.setAntwort3("Draco Malfoy");
    question1.setAntwort3_lsg(2);
    question1.setAntwort4("");
    question1.setAntwort4_lsg(0);
    question1.setGroupId(1);

    MCquestion question2= new MCquestion();
    question2.setThema("Harry Potter");
    question2.setAuthor("Tabea Schmid");
    question2.setAuthorID(user.getUsername());
    question2.setFrage("Wie heißt die Katze von Hermine Granger?");
    question2.setId(2);
    question2.setAntwort1("Mia");
    question2.setAntwort1_lsg(2);
    question2.setAntwort2("Krumbein");
    question2.setAntwort2_lsg(1);
    question2.setAntwort3("Tiger");
    question2.setAntwort3_lsg(2);
    question2.setAntwort4("");
    question2.setAntwort4_lsg(0);
    question2.setGroupId(1);

    mCquestionRepository.save(question1);
    mCquestionRepository.save(question2);



  }


}
