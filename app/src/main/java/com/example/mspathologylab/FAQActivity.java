package com.example.mspathologylab;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FAQActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    List<Faq> faqList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqactivity);

        recyclerView = findViewById(R.id.recyclerView);

        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        FaqAdapter FaqAdapter = new FaqAdapter(faqList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(FaqAdapter);
    }


    private void initData() {
        faqList = new ArrayList<>();
        faqList.add(new Faq("Is your Laboratory accredited?", "Yes, our laboratory is accredited by the NABL (National Accreditation Board of Testing and Calibration of Laboratories) India & College of American Pathologists (CAP). This accreditation process is carried out every 2 years for NABL & annually for CAP with periodic internal audits carried out twice a year. All our aforementioned tests are accredited by NABL & CAP."));

        faqList.add(new Faq("What are TruHealth Packages?", "TruHealth is an expert and comprehensive inner health wellness by Metropolis. It helps you monitor and maintain your inner health parameters better, so that you are ready to achieve your dreams, always. Metropolis TruHealth – Because Health is Everything."));

        faqList.add(new Faq("What is a SMART report?", "A SMART report covers a comprehensive view of your lifestyle-related diseases and risk analysis, health recommendations, reminders, and recommended reflex tests. Get regular communications to monitor and maintain your inner health."));

        faqList.add(new Faq("How long will it take for me to receive the reports?", "Reports will be delivered to your registered email id with a TAT (Turnaround time) of 24 hours."));

        faqList.add(new Faq("How can I download my test reports?", "We will deliver reports to your email id, check the email received from Metropolis India. You can also download it by signing in to your profile on our website or in the TruHealth App."));

        faqList.add(new Faq("Which is the right TruHealth package for me?", "You can select the best health package suited to you based on your gender, age, and lifestyle. Here is the link: /health-packages"));

        faqList.add(new Faq("Is home blood collection service free of charge?", "Additional Home Visit and Safety charges will be applicable for all tests and Health packages."));

        faqList.add(new Faq("What is the timing for blood collection from home?", "We collect samples from home between 7 AM to 11 PM (in Mumbai) and 7 AM to 9 PM in other cities. We offer Blood collection service on Sundays as well."));

        faqList.add(new Faq("Is a Doctor’s prescription required to avail the test?", "A Doctor’s prescription is not required if you avail any wellness package. For any illness test, a prescription might be required."));

        faqList.add(new Faq("Where are your test samples processed?", "Test samples are processed at our regional laboratories in various cities – Mumbai, Pune, Chennai, Bangalore, Delhi, and more. You can check out more information here – Our Labs."));


    }

}