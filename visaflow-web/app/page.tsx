import { Button } from "@/components/ui/button";
import { AppNavbar } from "@/components/app-navbar";
import Link from "next/link";

export default function Home() {
  return (
    <div className="flex flex-col min-h-screen">
      <AppNavbar />
      <main className="flex-1">
        <section className="space-y-6 pb-8 pt-6 md:pb-12 md:pt-10 lg:py-32">
          <div className="container flex max-w-[64rem] flex-col items-center gap-4 text-center">
            <h1 className="font-heading text-3xl sm:text-5xl md:text-6xl lg:text-7xl">
              Simpler US Visas for International Couples
            </h1>
            <p className="max-w-[42rem] leading-normal text-muted-foreground sm:text-xl sm:leading-8">
              VisaFlow automates your I-130 petition. No expensive lawyers, no confusing forms.
              Just answer simple questions and get your ready-to-submit package.
            </p>
            <div className="space-x-4">
              <Link href="/dashboard">
                <Button size="lg">Get Started</Button>
              </Link>
              <Button variant="outline" size="lg">Learn More</Button>
            </div>
          </div>
        </section>
      </main>
    </div>
  );
}
