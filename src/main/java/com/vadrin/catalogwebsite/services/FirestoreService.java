package com.vadrin.catalogwebsite.services;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.vadrin.catalogwebsite.models.RepositoryInfo;


@Service
public class FirestoreService {
  
  @Autowired
  Firestore firestore;

  public List<RepositoryInfo> getRepositoryInfos() throws InterruptedException, ExecutionException, FileNotFoundException {
    ApiFuture<QuerySnapshot> queryFuture = this.firestore.collection("CatalogWebsiteRepository").get();
    List<QueryDocumentSnapshot> queryDocuments = queryFuture.get().getDocuments();
    List<RepositoryInfo> toReturn = queryDocuments.stream().map(x -> x.toObject(RepositoryInfo.class)).collect(Collectors.toList());
    if(toReturn == null || toReturn.isEmpty())
      throw new FileNotFoundException();
    else {
      return toReturn;
    }
  }

  public void saveRepositoryInfo(RepositoryInfo toSave) throws InterruptedException, ExecutionException {
    this.firestore.document("CatalogWebsiteRepository/"+toSave.getName()).set(toSave).get();
  }
  
}
