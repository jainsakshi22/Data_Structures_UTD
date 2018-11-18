/**
 * Starter code for LP3
 *
 * @author
 */

// Change to your net id
package sxj180002;

// If you want to create additional classes, place them in this file as subclasses of MDS

import javafx.util.Pair;

import java.time.Month;
import java.util.*;

public class MDS {
    // Add fields of MDS here

    private Map<Long, Money> idMoneyMap;
    private Map<Money, Set<Long>> moneyIdMap;
    private Map<Long, List<Long>> idDescMap;
    private Map<Long, Set<Long>> descIdMap;
    private Map<Long, Set<Money>> descMoneyMap;


    // Constructors
    public MDS() {
        idMoneyMap = new TreeMap<>();
        moneyIdMap = new HashMap<>();
        idDescMap = new TreeMap<>();
        descIdMap = new HashMap<>();
        descMoneyMap = new HashMap<>();
    }

    /* Public methods of MDS. Do not change their signatures.
       __________________________________________________________________
       a. Insert(id,price,list): insert a new item whose description is given
       in the list.  If an entry with the same id already exists, then its
       description and price are replaced by the new values, unless list
       is null or empty, in which case, just the price is updated. 
       Returns 1 if the item is new, and 0 otherwise.
    */
    public int insert(long id, Money price, java.util.List<Long> list) {
        int result = 1;
        Money money = idMoneyMap.get(id);
        idMoneyMap.put(id, price);
        List<Long> oldDesc = idDescMap.get(id);
        if (null != list && list.size() > 0) idDescMap.put(id, list);
        for (Long x : oldDesc) {
            if (!list.contains(x)) {
                Set<Long> ids = descIdMap.get(x);
                ids.remove(id);
                if (ids.size() == 0) {
                    descIdMap.remove(x);
                    descMoneyMap.remove(x);
                }
            }
        }
        for (Long x : list) {
            if (oldDesc == null || !oldDesc.contains(x)) {
                Set<Long> ids = descIdMap.get(x);
                if (ids == null) {
                    ids = new HashSet<>();
                    descIdMap.put(x, ids);
                }
                ids.add(id);
            }
        }
        Set<Long> ids = moneyIdMap.get(price);
        if (null == ids) {
            ids = new HashSet<>();
            moneyIdMap.put(price, ids);
        }
        ids.add(id);

        if (money != null) {
            result = 0;
            Set<Long> ids1 = moneyIdMap.get(money);
            ids1.remove(id);
            if (ids1.size() == 0) {
                moneyIdMap.remove(money);
                for (Long desc : list) {
                    if (descIdMap.get(desc).size() == 1) {
                        Set<Money> moneys = descMoneyMap.get(desc);
                        moneys.remove(money);
                        moneys.add(price);

                    }
                }
            }
        }
        Set<Long> ids2 = moneyIdMap.get(price);
        if (ids2 == null) {
            ids2 = new HashSet<>();
            moneyIdMap.put(price, ids2);
        }
        ids2.add(id);
        for (Long x : list) {
            Set<Money> moneys = descMoneyMap.get(x);
            if (moneys == null) {
                moneys = new TreeSet<>();
                descMoneyMap.put(x, moneys);
            }
            moneys.add(price);
        }

        return result;
    }

    // b. Find(id): return price of item with given id (or 0, if not found).
    public Money find(long id) {
        Money money = idMoneyMap.get(Long.valueOf(id));
        return money == null ? new Money() : money;
    }

    /* 
       c. Delete(id): delete item from storage.  Returns the sum of the
       long ints that are in the description of the item deleted,
       or 0, if such an id did not exist.
    */
    public long delete(long id) {
        Money money = idMoneyMap.get(Long.valueOf(id));
        List<Long> items = idDescMap.get(Long.valueOf(id));
        if (money == null && (items == null || items.isEmpty())) return 0;
        long sum = 0;
        for (Long item : items) {
            sum += item;
        }
        money = idMoneyMap.remove(Long.valueOf(id));
        if (money != null) {
            TreeSet<Long> ids = (TreeSet<Long>) moneyIdMap.get(money);
            if (ids != null && ids.size() > 1) {
                ids.remove(Long.valueOf(id));
            } else {
                moneyIdMap.remove(money);
                for (Long item : items) {
                    TreeSet<Money> moneyTreeSet = (TreeSet<Money>) descMoneyMap.get(item);
                    if (moneyTreeSet != null && moneyTreeSet.size() > 1) {
                        moneyTreeSet.remove(money);
                    } else {
                        descMoneyMap.remove(item);
                    }
                }
            }
        }

        items = idDescMap.remove(Long.valueOf(id));
        for (Long item : items) {
            TreeSet<Long> ids = (TreeSet<Long>) descIdMap.get(item);
            if (ids != null && ids.size() > 1) {
                ids.remove(Long.valueOf(id));
            } else {
                descIdMap.remove(item);
                TreeSet<Money> moneyTreeSet = (TreeSet<Money>) descMoneyMap.get(item);
                if (moneyTreeSet != null && moneyTreeSet.size() > 1) {
                    moneyTreeSet.remove(money);
                } else {
                    descMoneyMap.remove(item);
                }
            }
        }

        return sum;
    }


    /*
       d. FindMinPrice(n): given a long int, find items whose description
       contains that number (exact match with one of the long ints in the
       item's description), and return lowest price of those items.
       Return 0 if there is no such item.
    */
    public Money findMinPrice(long n) {
        TreeSet<Money> money = (TreeSet<Money>) descMoneyMap.get(Long.valueOf(n));
        return money == null ? new Money() : money.first();
    }

    /* 
       e. FindMaxPrice(n): given a long int, find items whose description
       contains that number, and return highest price of those items.
       Return 0 if there is no such item.
    */
    public Money findMaxPrice(long n) {
        TreeSet<Money> money = (TreeSet<Money>) descMoneyMap.get(Long.valueOf(n));
        return money == null ? new Money() : money.last();
    }

    /* 
       f. FindPriceRange(n,low,high): given a long int n, find the number
       of items whose description contains n, and in addition,
       their prices fall within the given range, [low, high].
    */
    public int findPriceRange(long n, Money low, Money high) {
        int count = 0;
        TreeSet<Long> ids = (TreeSet<Long>) descIdMap.get(Long.valueOf(n));
        if (ids == null || ids.isEmpty()) return count;
        for (Long id : ids) {
            Money money = idMoneyMap.get(id);
            if (money.compareTo(low) >= 0 && money.compareTo(high) <= 0) {
                count++;
            }
        }
        return count;
    }

    /* 
       g. PriceHike(l,h,r): increase the price of every product, whose id is
       in the range [l,h] by r%.  Discard any fractional pennies in the new
       prices of items.  Returns the sum of the net increases of the prices.
    */
    public Money priceHike(long l, long h, double rate) {

        Money oldValue = null;
        Money newValue = null;
        double sum = 0;
        while (l <= h) {
            oldValue = idMoneyMap.get(Long.valueOf(l));
            if (oldValue != null) {
                if (oldValue.dollars() == 0 && oldValue.cents() != 0) {
                    int c = (int) (oldValue.cents() * (1 + (0.01) * rate));
                    long d = (c > 100) ? (c / 100) : 0;
                    if (c > 100) {
                        c = c % 100;
                    }
                    newValue = new Money(d, c);
                } else if (oldValue.dollars() != 0 && oldValue.cents() == 0) {
                    double d = (oldValue.dollars() * (1 + (0.01) * rate));
                    Pair<Long, Integer> value = getDollarsAndCents(Double.toString(d));
                    newValue = new Money(value.getKey(), value.getValue());
                } else if (oldValue.dollars() != 0 && oldValue.cents() != 0) {
                    String value = oldValue.toString();
                    double incValue = Double.valueOf(value) * (1 + (0.01) * rate);
                    Pair<Long, Integer> p = getDollarsAndCents(Double.toString(incValue));
                    newValue = new Money(p.getKey(), p.getValue());
                } else {
                    newValue = new Money();
                }
                sum += (Double.valueOf(newValue.toString()) - Double.valueOf(oldValue.toString()));
            }
            insert(l, newValue, null);
            l++;
        }
        return new Money(String.valueOf(sum));
    }

    private static Pair<Long, Integer> getDollarsAndCents(String moneyString) {
        String[] parts = moneyString.split(".");
        long dollar = Long.parseLong(parts[0]);
        int cents = 0;
        if (parts.length == 2) {
            if (parts[1].length() == 1) {
                cents = Integer.parseInt(parts[1]);
            } else {
                cents = Integer.parseInt(parts[1].substring(0, 2));
            }
        }
        return new Pair<>(dollar, cents);
    }

    /*
      h. RemoveNames(id, list): Remove elements of list from the description of id.
      It is possible that some of the items in the list are not in the
      id's description.  Return the sum of the numbers that are actually
      deleted from the description of id.  Return 0 if there is no such id.
    */
    public long removeNames(long id, java.util.List<Long> list) {
        long sum = 0;
        if (list == null || list.isEmpty()) return sum;
        List<Long> description = idDescMap.get(Long.valueOf(id));
        if (description == null || description.isEmpty()) return sum;
        for (Long item : list) {
            if (description.indexOf(item) != -1) {
                sum += item;
                if (description.size() > 1) {
                    description.remove(item);
                    TreeSet<Long> ids = (TreeSet<Long>) descIdMap.get(item);
                    if (ids != null && ids.size() > 1) {
                        ids.remove(Long.valueOf(id));
                        Money money = idMoneyMap.get(Long.valueOf(id));
                        if (money != null) {
                            TreeSet<Long> ids1 = (TreeSet<Long>) moneyIdMap.get(money);
                            if (ids1 != null && ids1.size() > 1) {
                                moneyIdMap.remove(Long.valueOf(id));
                            } else {
                                moneyIdMap.remove(money);
                                TreeSet<Money> moneyTreeSet = (TreeSet<Money>) descMoneyMap.get(item);
                                if (moneyTreeSet != null && moneyTreeSet.size() > 1) {
                                    moneyTreeSet.remove(money);
                                } else {
                                    descMoneyMap.remove(item);
                                }
                            }
                        }
                    } else {
                        descIdMap.remove(item);
                        descMoneyMap.remove(item);
                    }
                } else {
                    description = new LinkedList<>();
                    TreeSet<Long> ids = (TreeSet<Long>) descIdMap.get(item);
                    if (ids != null && ids.size() > 1) {
                        ids.remove(Long.valueOf(id));
                    } else {
                        descIdMap.remove(item);
                        descMoneyMap.remove(item);// Have to check it again.
                    }
                }
            }
        }
        return sum;
    }

    // Do not modify the Money class in a way that breaks LP3Driver.java
    public static class Money implements Comparable<Money> {
        long d;
        int c;

        public Money() {
            d = 0;
            c = 0;
        }

        public Money(long d, int c) {
            this.d = d;
            this.c = c;
        }

        public Money(String s) {
            String[] part = s.split("\\.");
            int len = part.length;
            if (len < 1) {
                d = 0;
                c = 0;
            } else if (part.length == 1) {
                d = Long.parseLong(s);
                c = 0;
            } else {
                d = Long.parseLong(part[0]);
                c = Integer.parseInt(part[1]);
            }
        }

        public long dollars() {
            return d;
        }

        public int cents() {
            return c;
        }

        public int compareTo(Money other) { // Complete this, if needed
            if (this.d == other.d && this.c == other.c) return 0;
            else if (this.d < other.d || this.c < other.c) return -1;
            else return 1;
        }

        public String toString() {
            //return (c != 0 ? d + "." + c : String.valueOf(d));
            return d + "." + c;
        }
    }

}
