package controller;

import model.ADT.MyIHeap;
import model.ADT.MyIMap;
import model.ADT.MyMap;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GarbageCollector {
    private MyIMap<String, IValue> symtable;
    private MyIHeap heap;
    public GarbageCollector(MyIMap<String, IValue> symtable, MyIHeap heap) {
        this.symtable = symtable;
        this.heap = heap;
    }
    MyIMap<Integer,IValue> safeGarbageCollector(List<Integer> symTableAddresses){
//        MyIMap<Integer, IValue> newHeap = new MyMap<Integer, IValue>();
//        for (int symTableAddress : symTableAddresses) {
//            if (this.heap.containsKey(symTableAddress)) {
//                newHeap.put(symTableAddress, this.heap.getValue(symTableAddress));
//            }
//        }
//        return newHeap;
        Map<Integer,IValue> newHeap = new HashMap<>();
        heap.getHeap().getKeys().stream()
                .filter(e->symTableAddresses.contains(e))
                        .forEach(e->newHeap.put(e, heap.getHeap().getValue(e)));
        return new MyMap(newHeap);
    }

    List<Integer> getAddressesFromSymTable(){
//        List<IValue> symTableValues = new ArrayList<>();
//        for (String key : this.symtable.getKeys())
//        {
//            symTableValues.add(this.symtable.getValue(key));
//        }
        return this.symtable.getKeys().stream().map(elem->symtable.getValue(elem)).flatMap(symTableValue->{
            List<Integer> symTableAddresses = new ArrayList<>();
            if (symTableValue instanceof RefValue) {
                symTableAddresses.add(((RefValue) symTableValue).getAddress());
                if (((RefValue) symTableValue).getLocationType() instanceof RefType)
                {
                    RefValue refValue = (RefValue) symTableValue;
                    while (refValue.getLocationType() instanceof RefType && this.heap.containsKey(refValue.getAddress()))
                    {
                        refValue = (RefValue) this.heap.getValue(refValue.getAddress());
                        symTableAddresses.add(refValue.getAddress());
                    }
                }
            }
            return symTableAddresses.stream();
        }).collect(Collectors.toList());
//        return this.symtable.getKeys().stream().map(elem->symtable.getValue(elem)).flatMap(symTableValue->{
//            List<IValue> symTableAddresses = new ArrayList<>();
//            if (symTableValue instanceof RefValue) {
//                symTableAddresses.add(((RefValue) symTableValue));
//                if (((RefValue) symTableValue).getLocationType() instanceof RefType)
//                {
//                    RefValue refValue = (RefValue) symTableValue;
//                    while (refValue.getLocationType() instanceof RefType && this.heap.containsKey(refValue.getAddress()))
//                    {
//                        refValue = (RefValue) this.heap.getValue(refValue.getAddress());
//                        symTableAddresses.add(refValue);
//                    }
//                }
//            }
//            return symTableAddresses;
//        }).collect(Collectors.toList());
    }
}