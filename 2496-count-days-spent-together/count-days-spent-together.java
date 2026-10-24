class Solution {
    public int countDaysTogether(String arriveAlice, String leaveAlice, String arriveBob, String leaveBob) {
        String start=arriveAlice.compareTo(arriveBob)>0?arriveAlice:arriveBob;
         String end=leaveAlice.compareTo(leaveBob)<0?leaveAlice:leaveBob;
         int startDay=dayofYear(start);
         int endDay= dayofYear(end);
        return Math.max(0,endDay-startDay+1);
    }
    public int dayofYear(String date){
        int[] days={31,28,31,30,31,30,31,31,30,31,30,31};
        int month=Integer.parseInt(date.substring(0,2));
         int day=Integer.parseInt(date.substring(3,5));
         int total=day;
         for(int i=0;i<month-1;i++){
            total+=days[i];
         }
         return total;
    }
}