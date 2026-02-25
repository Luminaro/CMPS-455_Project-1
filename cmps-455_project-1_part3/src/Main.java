public class Main {
    public static void main(String[] args) throws InterruptedException {
        int value_idx = -1;
        for(int i=0; i<args.length; i++){
            if(args[i].equals("-A") && i+1 <= args.length){
                value_idx = i+1;
            }
        }

        if(value_idx == -1) {
            System.out.println("Must enter a valid value for arg -A (1 or 2)");
            return;
        }

        int value = Integer.parseInt(args[value_idx]);
        System.out.printf("Value: %d\n", value);
        if(value != 1 && value != 2){
            System.out.println("Must enter a valid value for arg -A (1 or 2)");
            return;
        }

        switch(value){
            case 1:
                Part1.main(args);
                break;
            case 2:
                Part2.main(args);
                break;
            default:
                System.out.println("How did you get here?");
        }
    }
}
