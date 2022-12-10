import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    static int HeadMovesSeqFCFS[], HeadMovesSeqSSTF[], HeadMovesSeqSCAN[], HeadMovesSeqCSCAN[], HeadMovesSeqCLOOK[], HeadMovesSeqNewlyOptimized[];

    public static int FCFS(int Head, ArrayList<Integer> ReqQueue)
    {
        int TotalHeadMoves = 0;
        HeadMovesSeqFCFS = new int[ReqQueue.size()];
        if(ReqQueue.size() != 0)
        {
            HeadMovesSeqFCFS[0] = ReqQueue.get(0);
            TotalHeadMoves += Math.abs(Head - ReqQueue.get(0));
            for (int i = 0; i < ReqQueue.size() - 1; i++)
            {
                HeadMovesSeqFCFS[i + 1] = ReqQueue.get(i + 1);
                TotalHeadMoves += Math.abs(ReqQueue.get(i) - ReqQueue.get(i + 1));
            }
        }
        return TotalHeadMoves;
    }
    /*public static int BinarySearchSST(int head,ArrayList<Integer> ReqQueue)
    {
        int s = 0, e = ReqQueue.size() - 1, mid = s + (e - s) / 2, PrevMinMove = (int)1e9, CurrMinMove ,i = mid;
        for(;i < ReqQueue.size() - 1;i++)
            if(ReqQueue.get(i) != ReqQueue.get(i + 1)) { PrevMinMove = Math.abs(head - ReqQueue.get(i + 1)); break;}
        while(e > s)
        {
            CurrMinMove = Math.abs(head - ReqQueue.get(mid));
            if(CurrMinMove > PrevMinMove)
               s = mid + 1;
            else
            {  e = mid; PrevMinMove = CurrMinMove;}
            mid = s + (e - s) / 2;
        }
        return mid;
    }
    public static int SSTF(int Head, ArrayList<Integer> ReqQueue)
    {
        int TotalHeadMoves = 0, CurrSeqIdx = 0,IdxSST  ,HeadSST;
        HeadMovesSeqSSTF = new int[ReqQueue.size()];
        Collections.sort(ReqQueue);
        while(ReqQueue.size() != 0)
        {
            IdxSST = BinarySearchSST(Head,ReqQueue);
            HeadSST = ReqQueue.get(IdxSST);
            ReqQueue.remove(IdxSST);
            HeadMovesSeqFCFS[CurrSeqIdx] = HeadSST;
            TotalHeadMoves += Math.abs(Head - HeadSST);
            Head = HeadSST;
            CurrSeqIdx++;
        }
        return TotalHeadMoves;
    }*/
    public static int SSTF(int Head, ArrayList<Integer> ReqQueueIn)
    {
        ArrayList<Integer> ReqQueue = new ArrayList<>(ReqQueueIn);
        int TotalHeadMoves = 0, MinMoves, CurrHeadMoves, MinMovesRQ = -1, HeadMovesSeqIdx = 0;
        HeadMovesSeqSSTF = new int[ReqQueue.size()];
        while(ReqQueue.size() != 0)
        {
            MinMoves = (int)1e9;
            for (int i = 0; i < ReqQueue.size(); i++)
            {
                CurrHeadMoves = Math.abs(Head - ReqQueue.get(i));
                if(CurrHeadMoves < MinMoves)
                {
                    MinMoves = CurrHeadMoves;
                    MinMovesRQ = i;
                }
            }
            Head = ReqQueue.get(MinMovesRQ);
            ReqQueue.remove(MinMovesRQ);
            HeadMovesSeqSSTF[HeadMovesSeqIdx] = Head;
            TotalHeadMoves += MinMoves;
            HeadMovesSeqIdx++;
        }
        return TotalHeadMoves;
    }
    public static int SCAN(int Head, ArrayList<Integer> ReqQueueIn, boolean HeadDirection)/**true -> inc disk ptr / false -> dec disk ptr**/
    {
        ArrayList<Integer> ReqQueue = new ArrayList<>(ReqQueueIn);
        int TotalHeadMoves = 0 , FirstServedReqIdx, NxtHead, HeadMovesSeqIdx = 1;
        HeadMovesSeqSCAN = new int[ReqQueue.size() + 1];
        Collections.sort(ReqQueue);
        FirstServedReqIdx = -1 * Collections.binarySearch(ReqQueue,Head) - 1;
        if(FirstServedReqIdx == ReqQueue.size() ||(!HeadDirection && FirstServedReqIdx != 0))
            FirstServedReqIdx--;
        NxtHead = ReqQueue.get(FirstServedReqIdx);
        HeadMovesSeqSCAN[0] = NxtHead;
        TotalHeadMoves += Math.abs(Head - NxtHead);
        Head = NxtHead;
        if(HeadDirection)
        {
            for(int i = FirstServedReqIdx + 1; i < ReqQueue.size();i++,HeadMovesSeqIdx++)
            {
                NxtHead = ReqQueue.get(i);
                HeadMovesSeqSCAN[HeadMovesSeqIdx] = NxtHead;
                TotalHeadMoves += Math.abs(Head - NxtHead);
                Head = NxtHead;
            }
            for(int i = FirstServedReqIdx - 1; i >= 0;i--,HeadMovesSeqIdx++)
            {
                NxtHead = ReqQueue.get(i);
                HeadMovesSeqSCAN[HeadMovesSeqIdx] = NxtHead;
                TotalHeadMoves += Math.abs(Head - NxtHead);
                Head = NxtHead;
            }
            NxtHead = 0;
            HeadMovesSeqSCAN[HeadMovesSeqIdx] = NxtHead;
            TotalHeadMoves += Math.abs(Head - NxtHead);
            Head = NxtHead;
            HeadMovesSeqIdx++;
        }
        else
        {
            for(int i = FirstServedReqIdx - 1; i >= 0;i--,HeadMovesSeqIdx++)
            {
                NxtHead = ReqQueue.get(i);
                HeadMovesSeqSCAN[HeadMovesSeqIdx] = NxtHead;
                TotalHeadMoves += Math.abs(Head - NxtHead);
                Head = NxtHead;
            }
            NxtHead = 0;
            HeadMovesSeqSCAN[HeadMovesSeqIdx] = NxtHead;
            TotalHeadMoves += Math.abs(Head - NxtHead);
            Head = NxtHead;
            HeadMovesSeqIdx++;
            for(int i = FirstServedReqIdx + 1; i < ReqQueue.size();i++,HeadMovesSeqIdx++)
            {
                NxtHead = ReqQueue.get(i);
                HeadMovesSeqSCAN[HeadMovesSeqIdx] = NxtHead;
                TotalHeadMoves += Math.abs(Head - NxtHead);
                Head = NxtHead;
            }
        }
        return TotalHeadMoves;
    }
    public static int CSCAN(int Head, ArrayList<Integer> ReqQueueIn, boolean HeadDirection)/**true -> inc disk ptr / false -> dec disk ptr**/
    {
        ArrayList<Integer> ReqQueue = new ArrayList<>(ReqQueueIn);
        int TotalHeadMoves = 0, FirstServedReqIdx, NxtHead, HeadMovesSeqIdx = 1;
        HeadMovesSeqCSCAN = new int[ReqQueue.size() + 1];
        Collections.sort(ReqQueue);
        FirstServedReqIdx = -1 * Collections.binarySearch(ReqQueue, Head) - 1;
        if (FirstServedReqIdx == ReqQueue.size() || (!HeadDirection && FirstServedReqIdx != 0))
            FirstServedReqIdx--;
        NxtHead = ReqQueue.get(FirstServedReqIdx);
        HeadMovesSeqCSCAN[0] = NxtHead;
        TotalHeadMoves += Math.abs(Head - NxtHead);
        Head = NxtHead;
        if (HeadDirection)
        {
            for (int i = FirstServedReqIdx + 1; i < ReqQueue.size(); i++, HeadMovesSeqIdx++)
            {
                NxtHead = ReqQueue.get(i);
                HeadMovesSeqCSCAN[HeadMovesSeqIdx] = NxtHead;
                TotalHeadMoves += Math.abs(Head - NxtHead);
                Head = NxtHead;
            }
            NxtHead = 0;
            HeadMovesSeqCSCAN[HeadMovesSeqIdx] = NxtHead;
            TotalHeadMoves += Math.abs(Head - NxtHead);
            Head = NxtHead;
            HeadMovesSeqIdx++;
            for (int i = 0; i < FirstServedReqIdx; i++, HeadMovesSeqIdx++)
            {
                NxtHead = ReqQueue.get(i);
                HeadMovesSeqCSCAN[HeadMovesSeqIdx] = NxtHead;
                TotalHeadMoves += Math.abs(Head - NxtHead);
                Head = NxtHead;
            }
        }
        else
        {
            for (int i = FirstServedReqIdx - 1; i >= 0; i--, HeadMovesSeqIdx++)
            {
                NxtHead = ReqQueue.get(i);
                HeadMovesSeqCSCAN[HeadMovesSeqIdx] = NxtHead;
                TotalHeadMoves += Math.abs(Head - NxtHead);
                Head = NxtHead;
            }
            NxtHead = 0;
            HeadMovesSeqCSCAN[HeadMovesSeqIdx] = NxtHead;
            TotalHeadMoves += Math.abs(Head - NxtHead);
            Head = NxtHead;
            HeadMovesSeqIdx++;
            for (int i = ReqQueue.size() - 1; i > FirstServedReqIdx; i--, HeadMovesSeqIdx++)
            {
                NxtHead = ReqQueue.get(i);
                HeadMovesSeqCSCAN[HeadMovesSeqIdx] = NxtHead;
                TotalHeadMoves += Math.abs(Head - NxtHead);
                Head = NxtHead;
            }
        }
        return TotalHeadMoves;
    }
    public static int CLOOK(int Head, ArrayList<Integer> ReqQueueIn, boolean HeadDirection)/**true -> inc disk ptr / false -> dec disk ptr**/
    {
        ArrayList<Integer> ReqQueue = new ArrayList<>(ReqQueueIn);
        int TotalHeadMoves = 0, FirstServedReqIdx, NxtHead, HeadMovesSeqIdx = 1;
        HeadMovesSeqCLOOK = new int[ReqQueue.size()];
        Collections.sort(ReqQueue);
        FirstServedReqIdx = -1 * Collections.binarySearch(ReqQueue, Head) - 1;
        if (FirstServedReqIdx == ReqQueue.size() || (!HeadDirection && FirstServedReqIdx != 0))
            FirstServedReqIdx--;
        NxtHead = ReqQueue.get(FirstServedReqIdx);
        HeadMovesSeqCLOOK[0] = NxtHead;
        TotalHeadMoves += Math.abs(Head - NxtHead);
        Head = NxtHead;
        if (HeadDirection) {
            for (int i = FirstServedReqIdx + 1; i < ReqQueue.size(); i++, HeadMovesSeqIdx++)
            {
                NxtHead = ReqQueue.get(i);
                HeadMovesSeqCLOOK[HeadMovesSeqIdx] = NxtHead;
                TotalHeadMoves += Math.abs(Head - NxtHead);
                Head = NxtHead;
            }
            for (int i = 0; i < FirstServedReqIdx; i++, HeadMovesSeqIdx++)
            {
                NxtHead = ReqQueue.get(i);
                HeadMovesSeqCLOOK[HeadMovesSeqIdx] = NxtHead;
                TotalHeadMoves += Math.abs(Head - NxtHead);
                Head = NxtHead;
            }
        }
        else
        {
            for (int i = FirstServedReqIdx - 1; i >= 0; i--, HeadMovesSeqIdx++)
            {
                NxtHead = ReqQueue.get(i);
                HeadMovesSeqCLOOK[HeadMovesSeqIdx] = NxtHead;
                TotalHeadMoves += Math.abs(Head - NxtHead);
                Head = NxtHead;
            }
            for (int i = ReqQueue.size() - 1; i > FirstServedReqIdx; i--, HeadMovesSeqIdx++)
            {
                NxtHead = ReqQueue.get(i);
                HeadMovesSeqCLOOK[HeadMovesSeqIdx] = NxtHead;
                TotalHeadMoves += Math.abs(Head - NxtHead);
                Head = NxtHead;
            }
        }
        return TotalHeadMoves;
    }
    public static int NewlyOptimized(ArrayList<Integer> ReqQueueIn)
    {
        ArrayList<Integer> ReqQueue = new ArrayList<>(ReqQueueIn);
        int TotalHeadMoves = 0, Head = 0;
        HeadMovesSeqNewlyOptimized = new int[ReqQueue.size() + 1];
        if(ReqQueue.size() != 0)
        {
            Collections.sort(ReqQueue);
            HeadMovesSeqNewlyOptimized[0] = Head;
            HeadMovesSeqNewlyOptimized[1] = ReqQueue.get(0);
            TotalHeadMoves += Math.abs(Head - ReqQueue.get(0));
            for (int i = 0; i < ReqQueue.size() - 1; i++)
            {
                HeadMovesSeqNewlyOptimized[i + 2] = ReqQueue.get(i + 1);
                TotalHeadMoves += Math.abs(ReqQueue.get(i) - ReqQueue.get(i + 1));
            }
        }
        return TotalHeadMoves;
    }
    public static void main(String[] args) {
	    ArrayList<Integer> q = new ArrayList<>(List.of(38,180,130,10,50,15,190,90,150));
        System.out.println("FCFS " + FCFS(120,q));
        System.out.println(Arrays.toString(HeadMovesSeqFCFS));
        System.out.println("SSTF " + SSTF(120,q));
        System.out.println(Arrays.toString(HeadMovesSeqSSTF));

        System.out.println("SCAN " + SCAN(120,q,false));
        System.out.println(Arrays.toString(HeadMovesSeqSCAN));
        System.out.println("CSCAN " + CSCAN(120,q,false));
        System.out.println(Arrays.toString(HeadMovesSeqCSCAN));

        System.out.println("CLOOK " + CLOOK(120,q,false));
        System.out.println(Arrays.toString(HeadMovesSeqCLOOK));
        System.out.println("Newly Optimized " + NewlyOptimized(q));
        System.out.println(Arrays.toString(HeadMovesSeqNewlyOptimized));
    }
}
